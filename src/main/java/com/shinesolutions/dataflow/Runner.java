package com.shinesolutions.dataflow;

import com.google.cloud.dataflow.sdk.Pipeline;
import com.google.cloud.dataflow.sdk.options.PipelineOptions;
import com.google.cloud.dataflow.sdk.options.PipelineOptionsFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * Entry point for running dataflow pipelines. Looks for classes annotated with @DataflowPipeline
 * Created by gareth on 23/11/2016.
 */
public final class Runner {
    private final Logger logger = LoggerFactory.getLogger(Runner.class);

    private PipelineFinder pipelineFinder;
    private Object pipelineInstance;

    Runner(final PipelineFinder pipelineFinder) {
        this.pipelineFinder = pipelineFinder;
    }

    void run(final String pipelineName) throws Exception {
        Method pipelineMethod = pipelineFinder.find(pipelineName);
        if (pipelineMethod == null) {
            logger.error("Could not find a pipeline transformer annotated with @DataflowPipeline");
            return;
        }

        pipelineInstance = pipelineMethod.getDeclaringClass().newInstance();
        Pipeline dataflowPipeline = Pipeline.create(PipelineOptionsFactory.create());
        pipelineMethod.invoke(pipelineInstance, dataflowPipeline);
    }

    Object getPipelineInstance() {
        return this.pipelineInstance;
    }

    /**
     * Entry point for command-line invocation.
     * @param args - command-line args (see PipelineOptions)
     * @throws Exception - if something goes wrong in the pipeline.
     */
    public static void main(final String... args) throws Exception {
        PipelineOptions options = PipelineOptionsFactory.fromArgs(args).withValidation().create();
        Runner runner = new Runner(new AnnotatedPipelineFinder());
        runner.run(args[0]);
    }
}
