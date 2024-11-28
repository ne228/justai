package test.task.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetCallbackConfirmationCodeRequest extends Request {

    @JsonProperty("group_id")
    private Long groupId;

}
