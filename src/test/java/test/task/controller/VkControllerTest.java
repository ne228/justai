package test.task.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import test.task.response.backend.ErrorResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


@SpringBootTest(webEnvironment = RANDOM_PORT)
class VkControllerTest {

    @Autowired
    private VkController vkController;

    @LocalServerPort
    private int port;

    private String url;
    @Autowired
    TestRestTemplate template;

    @BeforeEach
    void setUp() {
        url = "http://localhost:" + port + "/api/vk/callback";
    }

    @Test
    void getMessage_requestNotContainsType_return400() throws Exception {
        // Arrange
        var jsonContent = """
                {
                    "group_id": 228436540,
                    "event_id": "030e1991a76c281f639bf0a63f8b4eaa4bdbd1a7",
                    "v": "5.199",
                }""";

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var entity = new HttpEntity<>(jsonContent, headers);

        // Act
        var response = template.exchange(url, HttpMethod.POST, entity, ErrorResponse.class);

        // Assert
        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    void testCallback_jsonParseError_return400() throws Exception {
        // Arrange
        // Нет закрывающей кавычки
        var jsonContent = """
                {
                    "group_id": 228436540,
                    "type": "new_message",
                    "event_id": "030e1991a76c281f639bf0a63f8b4eaa4bdbd1a7"",
                    "v": "5.199"
                }""";

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var entity = new HttpEntity<>(jsonContent, headers);

        // Act
        var response = template.exchange(url, HttpMethod.POST, entity, ErrorResponse.class);

        // Assert
        assertEquals(400, response.getStatusCode().value());
    }


    @Test
    void testCallBack_notValidJson_return400() throws Exception {
        // Arrange
        // Нет закрывающей кавычки
        var jsonContent = """
                {
                    "group_id": 228436540,
                    "type": "new_message",
                    "event_id": "030e1991a76c281f639bf0a63f8b4eaa4bdbd1a7"",
                    "v": "5.199"
                }""";

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var entity = new HttpEntity<>(jsonContent, headers);

        // Act
        var response = template.exchange(url, HttpMethod.POST, entity, ErrorResponse.class);

        // Assert
        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    void testCallBack_unknownType_return400() throws Exception {
        // Arrange
        var jsonContent = """
                {
                    "group_id": 228436540,
                    "type": "unknown_type",
                    "event_id": "030e1991a76c281f639bf0a63f8b4eaa4bdbd1a7",
                    "v": "5.199"
                }""";

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var entity = new HttpEntity<>(jsonContent, headers);

        // Act
        var response = template.exchange(url, HttpMethod.POST, entity, ErrorResponse.class);

        // Assert
        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    void testCallBack_messageNewEvent_return200() throws Exception {
        // Arrange
        var jsonContent = """
                {
                    {
                        "group_id": 228436540,
                        "type": "message_new",
                        "event_id": "030e1991a76c281f639bf0a63f8b4eaa4bdbd1a7",
                        "v": "5.199",
                        "object": {
                            "message": {
                                "date": 1733316765,
                                "from_id": 279926548,
                                "id": 227,
                                "version": 10000551,
                                "out": 0,
                                "important": false,
                                "is_hidden": false,
                                "attachments": [],
                                "conversation_message_id": 226,
                                "fwd_messages": [],
                                "text": "фывыфв",
                                "peer_id": 279926548,
                                "random_id": 0
                            },
                            "client_info": {
                                "button_actions": [
                                    "text",
                                    "vkpay",
                                    "open_app",
                                    "location",
                                    "open_link",
                                    "open_photo",
                                    "callback",
                                    "intent_subscribe",
                                    "intent_unsubscribe"
                                ],
                                "keyboard": true,
                                "inline_keyboard": true,
                                "carousel": true,
                                "lang_id": 0
                            }
                        },
                        "secret": "callbackSecret2222"
                    }
                }""";

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var entity = new HttpEntity<>(jsonContent, headers);

        // Act
        var response = template.exchange(url, HttpMethod.POST, entity, ErrorResponse.class);

        // Assert
        assertEquals(400, response.getStatusCode().value());
    }
}