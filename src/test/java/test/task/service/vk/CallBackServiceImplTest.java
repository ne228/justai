package test.task.service.vk;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;
import test.task.events.CallBackEvent;
import test.task.events.ConfirmationEvent;
import test.task.events.NewMessageEvent;
import test.task.exception.VkApiException;
import test.task.request.GetCallbackConfirmationCodeRequest;
import test.task.request.SendMessageRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CallBackServiceImplTest {

    @Mock
    private VkService vkService;

    @InjectMocks
    private CallBackServiceImpl callBackService;

    private String secret = "secret";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(callBackService, "secret", secret);
    }

    @Test
    void testProcessCallBack_ConfirmationEvent() throws JsonProcessingException, VkApiException {
        // Arrange
        ConfirmationEvent confirmationEvent = new ConfirmationEvent();
        confirmationEvent.setGroupId(12345L);
        confirmationEvent.setSecret(secret);

        GetCallbackConfirmationCodeRequest request = new GetCallbackConfirmationCodeRequest(12345L);
        when(vkService.getCallbackConfirmationCode(any())).thenReturn("123456");

        // Act
        String result = callBackService.processCallBack(confirmationEvent);

        // Assert
        assertEquals ("123456", result);
        verify(vkService, times(1)).getCallbackConfirmationCode(request);
    }

    @Test
    void testProcessCallBack_NewMessageEvent() throws JsonProcessingException, VkApiException {
        // Arrange
        NewMessageEvent newMessageEvent = new NewMessageEvent();
        newMessageEvent.setSecret(secret);
        newMessageEvent.getObject().getMessage().setFromId(1);
        newMessageEvent.getObject().getMessage().setText("Hello");

        SendMessageRequest sendMessageRequest = new SendMessageRequest(1L, "Вы сказали: Hello");
        when(vkService.sendMessage(any())).thenReturn("ok");

        // Act
        String result = callBackService.processCallBack(newMessageEvent);

        // Assert
        assertEquals("ok", result);
        verify(vkService, times(1)).sendMessage(any());
    }

    @Test
    void testProcessCallBack_InvalidSecret() {
        // Arrange
        ConfirmationEvent confirmationEvent = new ConfirmationEvent();
        confirmationEvent.setSecret("wrongSecret");

        // Act & Assert
        SecurityException exception = assertThrows(SecurityException.class, () -> callBackService.processCallBack(confirmationEvent));
        assertEquals("Неверный secret callBack", exception.getMessage());
    }

    @Test
    void testProcessCallBack_UnknownEventType() {
        // Arrange
        CallBackEvent unknownEvent = mock(CallBackEvent.class);
        when(unknownEvent.getSecret()).thenReturn(secret);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> callBackService.processCallBack(unknownEvent));
        assertEquals("Неизвестный тип события", exception.getMessage());
    }

    @Test
    void testProcessCallBack_VkApiException() throws JsonProcessingException, VkApiException {
        // Arrange
        NewMessageEvent newMessageEvent = new NewMessageEvent();
        newMessageEvent.setSecret(secret);
        newMessageEvent.getObject().getMessage().setFromId(1);
        newMessageEvent.getObject().getMessage().setText("Hello");

        SendMessageRequest sendMessageRequest = new SendMessageRequest(1L, "Вы сказали: Hello");
        when(vkService.sendMessage(any())).thenThrow(new VkApiException("test"));

        // Act & Assert
        VkApiException exception = assertThrows(VkApiException.class, () -> callBackService.processCallBack(newMessageEvent));

    }
}
