package test.task.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import test.task.config.ApplicationProperties;

@Data
public class Request {
    @JsonProperty("random_id")
    private Long randomId;

    @JsonProperty("v")
    private String version = "5.199";

    public Request() {
        this.randomId = System.currentTimeMillis();
        this.version = ApplicationProperties.getVersion();
    }


}
