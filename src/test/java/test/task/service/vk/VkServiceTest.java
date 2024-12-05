package test.task.service.vk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import test.task.exception.VkApiException;
import test.task.request.GetCallbackConfirmationCodeRequest;
import test.task.request.SendMessageRequest;
import test.task.response.vkApi.ConfirmationCodeResponse;
import test.task.response.vkApi.Error;
import test.task.response.vkApi.SendMessageResponse;
import test.task.service.utils.HttpService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class VkServiceTest {

    @Mock
    private HttpService httpService;

    @InjectMocks
    private VkServiceImpl vkService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendMessage_Success() throws Exception {
        // Arrange
        SendMessageRequest request = new SendMessageRequest(10L, "mess");
        SendMessageResponse response = new SendMessageResponse();
        response.setResponse("12345");
        when(httpService.sendPostRequest(anyString(), any(), eq(SendMessageResponse.class)))
                .thenReturn(ResponseEntity.ok(response));

        // Act
        String result = vkService.sendMessage(request);

        // Assert
        assertEquals("12345", result);
    }

    @Test
    void testSendMessage_Error() throws Exception {
        // Arrange
        SendMessageRequest request = new SendMessageRequest(10L, "mess");
        SendMessageResponse response = new SendMessageResponse();
        response.setError(new Error());
        when(httpService.sendPostRequest(anyString(), any(), eq(SendMessageResponse.class)))
                .thenReturn(ResponseEntity.ok(response));

        // Act & Assert
        assertThrows(VkApiException.class, () -> vkService.sendMessage(request));
    }

    @Test
    void testGetCallbackConfirmationCode_Success() throws Exception {
        // Arrange
        GetCallbackConfirmationCodeRequest request = new GetCallbackConfirmationCodeRequest(10L);
        ConfirmationCodeResponse response = new ConfirmationCodeResponse();
        response.setCode("123456");
        when(httpService.sendPostRequest(anyString(), any(), eq(ConfirmationCodeResponse.class)))
                .thenReturn(ResponseEntity.ok(response));

        // Act
        String result = vkService.getCallbackConfirmationCode(request);

        // Assert
        assertEquals("123456", result);
    }

    @Test
    void testGetCallbackConfirmationCode_Error() throws Exception {
        // Arrange
        GetCallbackConfirmationCodeRequest request = new GetCallbackConfirmationCodeRequest(10L);
        ConfirmationCodeResponse response = new ConfirmationCodeResponse();
        response.setError(new Error());
        when(httpService.sendPostRequest(anyString(), any(), eq(ConfirmationCodeResponse.class)))
                .thenReturn(ResponseEntity.ok(response));

        // Act & Assert
        VkApiException exception = assertThrows(VkApiException.class, () -> vkService.getCallbackConfirmationCode(request));
    }
}
