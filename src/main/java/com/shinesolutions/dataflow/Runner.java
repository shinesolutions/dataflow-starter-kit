package com.shinesolutions.dataflow;

import com.google.cloud.dataflow.sdk.Pipeline;
import com.google.cloud.dataflow.sdk.options.PipelineOptions;
import com.google.cloud.dataflow.sdk.options.PipelineOptionsFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * Entry point for running dataflow pipelines. Looks for classes annotated with @DataflowTransformer
 * Created by gareth on 23/11/2016.
 */
public final class Runner {
    private final Logger logger = LoggerFactory.getLogger(Runner.class);

    private Pipeline dataflowPipeline;
    private TransformFinder transformFinder;
    private Object transformer;

    Runner(final Pipeline dataflowPipeline, final TransformFinder transformFinder) {
        this.dataflowPipeline = dataflowPipeline;
        this.transformFinder = transformFinder;
    }

    void run(final String pipelineName) throws Exception {
        Method transformMethod = transformFinder.find(pipelineName);
        if (transformMethod == null) {
            logger.error(
                    "Could not find a pipeline transformer annotated with @DataflowTransformer named {}",
                    pipelineName
            );
            throw new IllegalArgumentException("No transformer named " + pipelineName + " found.");
        }

        transformer = transformMethod.getDeclaringClass().newInstance();
        transformMethod.invoke(transformer, dataflowPipeline);
        dataflowPipeline.run();
    }

    Object getTransformer() {
        return transformer;
    }

    /**
     * Entry point for command-line invocation.
     * @param args - command-line args (see PipelineOptions)
     * @throws Exception - if something goes wrong in the pipeline.
     */
    public static void main(final String... args) throws Exception {
        PipelineOptions options = PipelineOptionsFactory.fromArgs(args).withValidation().create();
        Pipeline pipeline = Pipeline.create(options);
        Runner runner = new Runner(pipeline, new AnnotatedTransformFinder());
        runner.run(args[0]);
    }
}
