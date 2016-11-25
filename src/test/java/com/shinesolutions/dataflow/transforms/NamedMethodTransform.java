package com.shinesolutions.dataflow.transforms;

import com.google.cloud.dataflow.sdk.Pipeline;
import com.shinesolutions.dataflow.DataflowTransformer;

/**
 * Created by garethjones on 25/11/16.
 */
@DataflowTransformer( name = "named-method", method = "rumplestiltskin")
public class NamedMethodTransform {

    public void transform(Pipeline pipeline) {
        //not this one
    }

    public void rumplestiltskin(Pipeline pipeline, Object something) {
        //not this one
    }

    public void rumplestiltskin(Pipeline pipeline) {
        //this is the one.
    }

    public String rumplestiltskin() {
        //nah.
        return null;
    }
}
