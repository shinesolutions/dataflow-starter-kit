package com.shinesolutions.dataflow.transforms;

import com.google.cloud.dataflow.sdk.Pipeline;
import com.shinesolutions.dataflow.DataflowTransformer;

/**
 * Created by garethjones on 25/11/16.
 */
@DataflowTransformer(name = "bad-arguments")
public class BadArgumentsTransform {

    public void transform(Pipeline pipeline, Object somethingElse) {
        //this should not be matched.
    }

    public void transform() {
        //this should not be matched either.
    }

    public void transform(String nope) {
        //definitely not.
    }

    public String transform(Pipeline pipeline) {
        //nice try, but no.
        return "";
    }

}
