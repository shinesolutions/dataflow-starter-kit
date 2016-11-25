package com.shinesolutions.dataflow.transforms;

import com.google.cloud.dataflow.sdk.Pipeline;
import com.shinesolutions.dataflow.DataflowTransformer;

/**
 * Created by garethjones on 25/11/16.
 */
@DataflowTransformer(name="test-without-method")
public class AnotherTestTransform {

    public void doesNotHaveATransformMethod(Pipeline pipeline) {
        //this will never get called.
    }
}
