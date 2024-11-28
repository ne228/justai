package test.task.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@NoArgsConstructor
public class NewMessageEvent extends CallBackEvent {


    @JsonProperty("event_id")
    private String eventId;


    @JsonProperty("object")
    private ObjectDetails object = new ObjectDetails();

    @Data
    @NoArgsConstructor
    public static class ObjectDetails {

        @JsonProperty("message")
        private Message message = new Message();

        @JsonProperty("client_info")
        private ClientInfo clientInfo = new ClientInfo();

        @Data
        @NoArgsConstructor
        public static class Message {

            @JsonProperty("date")
            private long date;

            @JsonProperty("from_id")
            private long fromId;

            @JsonProperty("id")
            private long id;

            @JsonProperty("version")
            private long version;

            @JsonProperty("out")
            private boolean out;

            @JsonProperty("important")
            private boolean important;

            @JsonProperty("is_hidden")
            private boolean isHidden;

            @JsonProperty("attachments")
            private List<Object> attachments;

            @JsonProperty("conversation_message_id")
            private long conversationMessageId;

            @JsonProperty("fwd_messages")
            private List<Object> fwdMessages;

            @JsonProperty("text")
            private String text;

            @JsonProperty("peer_id")
            private long peerId;

            @JsonProperty("random_id")
            private long randomId;
        }

        @Data
        @NoArgsConstructor
        public static class ClientInfo {

            @JsonProperty("button_actions")
            private List<String> buttonActions;

            @JsonProperty("keyboard")
            private boolean keyboard;

            @JsonProperty("inline_keyboard")
            private boolean inlineKeyboard;

            @JsonProperty("carousel")
            private boolean carousel;

            @JsonProperty("lang_id")
            private int langId;
        }
    }
}
