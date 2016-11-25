package com.shinesolutions.dataflow;

import com.google.cloud.dataflow.sdk.Pipeline;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Created by gareth on 23/11/2016.
 */
public class AnnotatedTransformFinder implements TransformFinder {

    private static final Logger LOG = LoggerFactory.getLogger(AnnotatedTransformFinder.class);

    /**
     * Looks for classes annotated with @DataflowPipeline, and returns the
     * method to invoke the transform on the named pipeline.
     * @param name - the pipeline to find
     * @return
     */
    @Override
    public final Method find(final String name) {
        List<Class> transformerClasses = new ArrayList<>();
        new FastClasspathScanner()
                .matchClassesWithAnnotation(
                        DataflowTransformer.class,
                        transformerClasses::add
                ).scan();

        LOG.debug("Found transformerClasses: {}", transformerClasses);

        Method transform = transformerClasses
                .stream()
                .filter(hasDataflowTransformerAnnotationWithName(name))
                .map(this::findMethodForClass)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);

        return transform;
    }

    private Predicate<Class> hasDataflowTransformerAnnotationWithName(final String name) {
        return (aClass) -> {
            Annotation annotation = aClass.getAnnotation(DataflowTransformer.class);
            return ((DataflowTransformer) annotation).name().equalsIgnoreCase(name);
        };
    }

    private Method findMethodForClass(final Class aClass) {
        Annotation annotation = aClass.getAnnotation(DataflowTransformer.class);
        try {
            Method method = aClass.getMethod(
                    ((DataflowTransformer) annotation).method(),
                    Pipeline.class
            );
            if (Void.TYPE.equals(method.getReturnType())) {
                return method;
            } else {
                LOG.warn(
                        "Wrong return type (wanted void) on method {} on {}",
                        ((DataflowTransformer) annotation).method(),
                        aClass
                );
            }
        } catch (NoSuchMethodException e) {
            LOG.warn(
                    "Could not find method called {} on {}",
                    ((DataflowTransformer) annotation).method(),
                    aClass
            );
        }
        return null;
    }
}
