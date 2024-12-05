package test.task.response.vkApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SendMessageResponse extends Response {

    @JsonProperty("response")
    private String response;
}
