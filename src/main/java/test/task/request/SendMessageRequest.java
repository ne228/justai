package test.task.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SendMessageRequest extends Request {

    @JsonProperty("user_id")
    private Long userId;


    @JsonProperty("peer_id")
    private String peerId;

    @JsonProperty("peer_ids")
    private String peerIds;

    @JsonProperty("domain")
    private String domain;

    @JsonProperty("chat_id")
    private Integer chatId;

    @JsonProperty("user_ids")
    private String userIds;

    @JsonProperty("message")
    private String message;

    @JsonProperty("guid")
    private Integer guid;

    @JsonProperty("lat")
    private String lat;

    @JsonProperty("long")
    private String longitude;

    @JsonProperty("attachment")
    private String attachment;

    @JsonProperty("reply_to")
    private Integer replyTo;

    @JsonProperty("forward_messages")
    private String forwardMessages;

    @JsonProperty("forward")
    private String forward;

    @JsonProperty("sticker_id")
    private Integer stickerId;

    @JsonProperty("group_id")
    private Integer groupId;

    @JsonProperty("keyboard")
    private String keyboard;

    @JsonProperty("template")
    private String template;

    @JsonProperty("payload")
    private String payload;

    @JsonProperty("content_source")
    private String contentSource;

    @JsonProperty("dont_parse_links")
    private Boolean dontParseLinks;

    @JsonProperty("disable_mentions")
    private Boolean disableMentions;

    @JsonProperty("intent")
    private String intent;

    @JsonProperty("subscribe_id")
    private Integer subscribeId;

    public SendMessageRequest(Long userId, String message) {
        this.userId = userId;
        this.message = message;
    }

    @Override
    public String toString() {
        return "SendMessageRequest{" +
                "userId=" + userId +
                ", peerId='" + peerId + '\'' +
                ", peerIds='" + peerIds + '\'' +
                ", domain='" + domain + '\'' +
                ", chatId=" + chatId +
                ", userIds='" + userIds + '\'' +
                ", message='" + message + '\'' +
                ", guid=" + guid +
                ", lat='" + lat + '\'' +
                ", longitude='" + longitude + '\'' +
                ", attachment='" + attachment + '\'' +
                ", replyTo=" + replyTo +
                ", forwardMessages='" + forwardMessages + '\'' +
                ", forward='" + forward + '\'' +
                ", stickerId=" + stickerId +
                ", groupId=" + groupId +
                ", keyboard='" + keyboard + '\'' +
                ", template='" + template + '\'' +
                ", payload='" + payload + '\'' +
                ", contentSource='" + contentSource + '\'' +
                ", dontParseLinks=" + dontParseLinks +
                ", disableMentions=" + disableMentions +
                ", intent='" + intent + '\'' +
                ", subscribeId=" + subscribeId +
                ", version='" + getVersion() + '\'' +
                ", randomId='" + getRandomId() + '\'' +
                '}';
    }
}
