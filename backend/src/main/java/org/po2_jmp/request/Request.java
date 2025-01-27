package org.po2_jmp.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents an abstract base class for all request types in the application.
 * <p>
 * This class provides a common structure for requests by including a {@code command} field,
 * which specifies the operation associated with the request.
 * </p>
 */
@Getter
@ToString
@EqualsAndHashCode
public abstract class Request {

    private final String command;

    /**
     * Constructs a {@code Request} with the specified command.
     *
     * @param command the command for the request, specifying the operation to perform.
     */
    @JsonCreator
    public Request(@JsonProperty("command") String command) {
        this.command = command;
    }

}
