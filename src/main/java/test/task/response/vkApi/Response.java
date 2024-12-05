package test.task.response.vkApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Response {

    @JsonProperty("error")
    private Error error;
}
