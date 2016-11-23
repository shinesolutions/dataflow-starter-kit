package com.shinesolutions.dataflow;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Tells the dataflow-starter-kit framework that this class requires a
 * pipeline to be set up and passed in.
 * Created by gareth on 23/11/2016.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataflowPipeline {
    /**
     * @return - name of the pipeline
     */
    String name();

    /**
     * @return - method name to invoke, defaults to "transform"
     */
    String method() default "transform";
}
