package com.shinesolutions.dataflow;

import com.google.cloud.dataflow.sdk.Pipeline;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

/**
 * Created by gareth on 23/11/2016.
 */
public class RunnerTest {

    @Test
    public void shouldUsePipelineFinderToDiscoverNamedPipeline() throws Exception {

        final String[] pipelineName = new String[1];
        Runner runner = new Runner(name -> {
            pipelineName[0] = name;
            return null;
        });

        runner.run("cheese");

        assertEquals("Pipeline name", "cheese", pipelineName[0]);
    }

    @Test
    public void shouldPassConfiguredPipelineToPipelineMethod() throws Exception {
        Runner runner = new Runner(name -> {
            try {
                return FakePipeline.class.getMethod("transform", Pipeline.class);
            } catch (NoSuchMethodException e) {
                fail("Something went horribly wrong, " + e.getMessage());
                return null;
            }
        });

        runner.run("whatever");

        Pipeline actual = ((FakePipeline) runner.getPipelineInstance()).pipeline;
        assertNotNull("there should be a pipeline", actual);
    }


    static class FakePipeline {
        Pipeline pipeline;

        public FakePipeline() {}

        public void transform(Pipeline pipeline) {
            this.pipeline = pipeline;
        }
    }
}
