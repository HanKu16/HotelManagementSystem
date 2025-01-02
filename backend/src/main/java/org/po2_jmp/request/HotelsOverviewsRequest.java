package org.po2_jmp.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper=true)
public class HotelsOverviewsRequest extends Request {

    @JsonCreator
    public HotelsOverviewsRequest(
            @JsonProperty("command") String command) {
        super(command);
    }

}
