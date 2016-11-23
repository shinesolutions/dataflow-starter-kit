package com.shinesolutions.dataflow;

import java.lang.reflect.Method;

/**
 * Created by gareth on 23/11/2016.
 */
public class AnnotatedPipelineFinder implements PipelineFinder {

    /**
     * Looks for classes annotated with @DataflowPipeline, and returns the
     * method to invoke the transform on the named pipeline.
     * @param name - the pipeline to find
     * @return
     */
    @Override
    public final Method find(final String name) {
        return null;
    }
}
