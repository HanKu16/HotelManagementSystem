package org.po2_jmp.controller.helper.contract;

/**
 * Interface for generating a response to valid requests.
 * <p>
 * Implementations of this interface are responsible for processing valid
 * requests and returning an appropriate response in the form of a string.
 * The response may include information or actions resulting from handling
 * the valid request.
 */
public interface ValidRequestResponder {

    /**
     * Generates a response for the provided valid request.
     * <p>
     * This method processes the given request string and returns a response
     * string that represents the outcome or result of handling the request.
     *
     * @param request the input request string that has been validated as valid
     * @return a string representing the response to the valid request
     */
    String respond(String request);

}
