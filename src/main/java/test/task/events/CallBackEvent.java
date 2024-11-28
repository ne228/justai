package test.task.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CallBackEvent {
    @JsonProperty("type")
    private String type;

    @JsonProperty("group_id")
    private Long groupId;

    @JsonProperty("secret")
    private String secret;

    @JsonProperty("v")
    private String v;



}
