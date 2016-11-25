package com.shinesolutions.dataflow.transforms;

import com.google.cloud.dataflow.sdk.Pipeline;
import com.shinesolutions.dataflow.DataflowTransformer;

/**
 * Created by garethjones on 24/11/16.
 */
@DataflowTransformer(name = "test")
public class TestTransformThing {

    public void transform(Pipeline pipeline) {
        //does nothing - it's just for the test to find.
    }
}
