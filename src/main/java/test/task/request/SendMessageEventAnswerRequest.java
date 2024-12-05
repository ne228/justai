package test.task.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SendMessageEventAnswerRequest extends Request {
    @JsonProperty("event_id")
    private String eventId;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("peer_id")
    private Long peerId;

    @JsonProperty("event_data")
    private String eventData;

    public SendMessageEventAnswerRequest(String eventId, Long userId, Long peerId) {
        this.eventId = eventId;
        this.userId = userId;
        this.peerId = peerId;
    }

    @Override
    public String toString() {
        return "SendMessageEventAnswerRequest{" +
                "eventId='" + eventId + '\'' +
                ", userId=" + userId +
                ", peerId=" + peerId +
                ", eventData='" + eventData + '\'' +
                ", version='" + getVersion() + '\'' +
                ", randomId='" + getRandomId() + '\'' +
                '}';
    }
}
