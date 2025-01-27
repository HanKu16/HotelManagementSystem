package org.po2_jmp.response;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents a standard response structure for API responses.
 * <p>
 * This class encapsulates the response status and a message.
 * </p>
 */
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Response {

    private final ResponseStatus status;
    private final String message;

}
