package org.po2_jmp.controller.helper.contract;

import org.po2_jmp.controller.helper.RequestValidationResult;

/**
 * Interface for generating a response to invalid requests.
 */
public interface InvalidRequestResponder {

    /**
     * Generates a response for invalid request based on validation result.
     * @param validationResult the result of validating the request
     * @return a string representation of the response for the invalid request
     */
    String respond(RequestValidationResult validationResult);

}
