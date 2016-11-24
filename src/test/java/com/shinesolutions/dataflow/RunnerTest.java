package com.shinesolutions.dataflow;

import com.google.cloud.dataflow.sdk.Pipeline;
import com.google.cloud.dataflow.sdk.testing.TestPipeline;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by gareth on 23/11/2016.
 */
public class RunnerTest {

    @Test
    public void shouldUsePipelineFinderToDiscoverNamedPipeline() throws Exception {

        final String[] pipelineName = new String[1];
        Runner runner = new Runner(TestPipeline.create(), name -> {
            pipelineName[0] = name;
            try {
                return FakeTransformer.class.getMethod("transform", Pipeline.class);
            } catch (NoSuchMethodException e) {
                fail("This should not have happened " + e.getMessage());
                return null;
            }
        });

        runner.run("cheese");

        assertEquals("Pipeline name", "cheese", pipelineName[0]);
    }

    @Test
    public void shouldPassConfiguredPipelineToPipelineMethod() throws Exception {
        Runner runner = new Runner(TestPipeline.create(), name -> {
            try {
                return FakeTransformer.class.getMethod("transform", Pipeline.class);
            } catch (NoSuchMethodException e) {
                fail("Something went horribly wrong, " + e.getMessage());
                return null;
            }
        });

        runner.run("whatever");

        Pipeline actual = ((FakeTransformer) runner.getTransformer()).pipeline;
        assertNotNull("there should be a pipeline", actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfPipelineIsNotFound() throws Exception {
        Runner runner = new Runner(null, name -> null);
        runner.run("does not exist");
    }


    static class FakeTransformer {
        Pipeline pipeline;

        public FakeTransformer() {}

        public void transform(Pipeline pipeline) {
            this.pipeline = pipeline;
        }
    }

}
