package test.task.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.ThreadLocalRandom;

public class Request {
    @JsonProperty("random_id")
    private Integer randomId;

    @JsonProperty("v")
    private String version = "5.199";


    public Integer getRandomId() {
        return ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE);
    }

    public void setRandomId(Integer randomId) {
        this.randomId = randomId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion( String version) {
        this.version = version;
    }
}
