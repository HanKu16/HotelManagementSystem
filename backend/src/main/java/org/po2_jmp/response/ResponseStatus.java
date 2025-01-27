package org.po2_jmp.response;

/**
 * Enum representing the possible status codes for an API response.
 * <p>
 * The {@code ResponseStatus} enum defines a set of statuses used in API responses
 * to indicate the outcome of an operation.
 *
 * <p>Possible values:</p>
 * <ul>
 *   <li>{@code OK} - The request was successful and the response is valid.</li>
 *   <li>{@code CREATED} - The resource was successfully created.</li>
 *   <li>{@code BAD_REQUEST} - The request was invalid.</li>
 *   <li>{@code UNAUTHORIZED} - The request lacks valid authentication credentials.</li>
 *   <li>{@code NOT_FOUND} - The requested resource could not be found.</li>
 *   <li>{@code METHOD_NOT_ALLOWED} - The method used is not allowed for the resource.</li>
 *   <li>{@code INTERNAL_SERVER_ERROR} - A generic error occurred on the server side.</li>
 *   <li>{@code NOT_IMPLEMENTED} - The requested functionality is not yet implemented.</li>
 * </ul>
 */
public enum ResponseStatus {

    OK,
    CREATED,
    BAD_REQUEST,
    UNAUTHORIZED,
    NOT_FOUND,
    METHOD_NOT_ALLOWED,
    INTERNAL_SERVER_ERROR,
    NOT_IMPLEMENTED;

}
