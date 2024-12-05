package test.task.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public abstract class Request {
    @JsonProperty("random_id")
    private Long randomId;

    @JsonProperty("v")
    private String version = "5.199";

    public Request() {
        this.randomId = System.currentTimeMillis();
    }


}
