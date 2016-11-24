package com.shinesolutions.dataflow;

import com.google.cloud.dataflow.sdk.Pipeline;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gareth on 23/11/2016.
 */
public class AnnotatedTransformFinder implements TransformFinder {

    private final Logger log = LoggerFactory.getLogger(AnnotatedTransformFinder.class);

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

        log.debug("Found transformerClasses: {}", transformerClasses);

        Method transform = transformerClasses
                .stream()
                .filter(aClass -> {
                    Annotation annotation = aClass.getAnnotation(DataflowTransformer.class);
                    return ((DataflowTransformer) annotation).name().equalsIgnoreCase(name);
                })
                .map(aClass -> {
                    Annotation annotation = aClass.getAnnotation(DataflowTransformer.class);
                    try {
                        return aClass.getMethod(
                                ((DataflowTransformer) annotation).method(),
                                Pipeline.class
                        );
                    } catch (NoSuchMethodException e) {
                        log.warn(
                                "Could not find method called {} on {}",
                                ((DataflowTransformer) annotation).method(),
                                aClass
                        );
                    }
                    return null;
                })
                .findFirst()
                .orElse(null);

        return transform;
    }
}
