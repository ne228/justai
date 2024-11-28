package test.task.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class ConfirmationCodeResponse extends Response {
    @JsonProperty("response")
    private Content response;

    public String getCode() {
        return response.code;
    }

    public void setCode(String code) {
        if (response == null)
            response = new Content();
        response.code = code;
    }
}

class Content {
    @JsonProperty("code")
    String code;
}
