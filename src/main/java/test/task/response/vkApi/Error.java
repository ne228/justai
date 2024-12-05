package test.task.response.vkApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class Error {

    @JsonProperty("error_code")
    private int errorCode;

    @JsonProperty("error_msg")
    private String errorMessage;

}
