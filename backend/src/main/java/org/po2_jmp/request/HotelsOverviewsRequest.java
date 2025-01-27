package org.po2_jmp.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents a request to retrieve overviews of multiple hotels.
 * <p>
 * This class extends the {@link Request} class and serves as a specialized request
 * that requires only a command parameter.
 */
@Getter
@ToString
@EqualsAndHashCode(callSuper=true)
public class HotelsOverviewsRequest extends Request {

    /**
     * Constructs a {@code HotelsOverviewsRequest} with the specified command.
     *
     * @param command the command associated with the request (inherited from {@link Request}).
     */
    @JsonCreator
    public HotelsOverviewsRequest(
            @JsonProperty("command") String command) {
        super(command);
    }

}
