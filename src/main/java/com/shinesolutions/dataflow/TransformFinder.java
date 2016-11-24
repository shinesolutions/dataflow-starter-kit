package com.shinesolutions.dataflow;

import java.lang.reflect.Method;

/**
 * Created by gareth on 23/11/2016.
 */
public interface TransformFinder {

    /**
     * Given a named pipeline returns the method to invoke.
     * @param name - the pipeline name
     * @return - the method to be invoked with a Pipeline argument
     */
    Method find(String name);
}
