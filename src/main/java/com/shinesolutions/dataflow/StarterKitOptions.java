package com.shinesolutions.dataflow;

import com.google.cloud.dataflow.sdk.options.DataflowPipelineOptions;
import com.google.cloud.dataflow.sdk.options.Description;
import com.google.cloud.dataflow.sdk.options.Validation;

/**
 * Custom command-line arguments for the Runner.
 * Provides a "--transformer=name" argument.
 * Created by garethjones on 25/11/16.
 */
public interface StarterKitOptions extends DataflowPipelineOptions {
    /**
     * Holds the value supplied in the "--transformer=" command-line option.
     * @return the name attribute of the DataflowTransformer annotation to be used.
     */
    @Description("The name given to the @DataflowTransformer annotated class")
    @Validation.Required
    String getTransformer();

    /**
     * Set the value from the command-line argument.
     * @param transformer - the name attribute of a DataflowTransformer-annotated class.
     */
    void setTransformer(String transformer);
}
