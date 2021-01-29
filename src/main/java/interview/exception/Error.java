package interview.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class Error {

    @JsonProperty("code")
    private int code;

    @JsonProperty("message")
    private String message;
}
