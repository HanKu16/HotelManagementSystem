package org.po2_jmp.controller.helper.contract;

import org.po2_jmp.controller.helper.RequestValidationResult;

/**
 * Interface for validating requests.
 */
public interface RequestValidator {

    /**
     * Checks the input request against predefined validation
     * rules and returns a {@link RequestValidationResult} containing the
     * outcome of the validation. The result may indicate whether the request
     * is valid and provide additional details about any issues.
     *
     * @param request the input request string to validate
     * @return a {@link RequestValidationResult} containing the outcome of
     *         the validation process
     */
    RequestValidationResult validate(String request);

}
