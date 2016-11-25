package com.shinesolutions.dataflow.transforms;

import com.google.cloud.dataflow.sdk.Pipeline;
import com.shinesolutions.dataflow.DataflowTransformer;

/**
 * Created by garethjones on 25/11/16.
 */
@DataflowTransformer(
        name="wrong-method",
        method="mangle"
)
public class WrongMethodTransform {

    public void transform(Pipeline pipeline) {
        //should never be found because it doesn't match the name in the annotation.
    }
}
