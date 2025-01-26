package org.po2_jmp.controller.helper;

/**
 * Enum representing the possible results of validating a request.
 * <p>
 * This enum is used to indicate the outcome of a request validation
 * process. It helps to determine whether the request is well-formed,
 * contains the necessary command, or is null.
 * </p>
 */
public enum RequestValidationResult {

    BAD_FORMAT,
    NO_COMMAND,
    IS_NULL,
    OK

}
