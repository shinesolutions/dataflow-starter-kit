package com.shinesolutions.example;

import com.google.cloud.dataflow.sdk.Pipeline;
import com.google.cloud.dataflow.sdk.transforms.Create;
import com.google.cloud.dataflow.sdk.transforms.DoFn;
import com.google.cloud.dataflow.sdk.transforms.ParDo;
import com.shinesolutions.dataflow.DataflowTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gareth on 23/11/2016.
 */
@DataflowTransformer(name = "example")
public final class Example {
    private static final Logger LOG = LoggerFactory.getLogger(Example.class);

    /**
     * Apply transformations to the passed-in pipeline.
     * @param pipeline - the pipeline to transform
     */
    @SuppressWarnings("checkstyle:finalParameters")
    public void transform(final Pipeline pipeline) {
        pipeline.apply(Create.of("Hello", "World"))
                .apply(ParDo.of(new DoFn<String, String>() {
                    @Override
                    public void processElement(ProcessContext c) {
                        c.output(c.element().toUpperCase());
                    }
                }))
                .apply(ParDo.of(new DoFn<String, Void>() {
                    @Override
                    public void processElement(ProcessContext c)  {
                        LOG.info(c.element());
                    }
                }));
    }
}
