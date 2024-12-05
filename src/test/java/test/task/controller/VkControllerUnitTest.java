package test.task.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import test.task.events.CallBackEvent;
import test.task.exception.UnknownEventTypeException;
import test.task.exception.VkApiException;
import test.task.service.vk.CallBackService;
import test.task.service.vk.EventMappingService;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class VkControllerUnitTest {

    @Mock
    private EventMappingService eventMappingService;
    @Mock
    private CallBackService callBackService;
    @InjectMocks
    private VkController vkController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getMessage_ValidCallback_ReturnsOk() throws JsonProcessingException, VkApiException, UnknownEventTypeException {
        // Arrange
        Map<String, Object> callback = new HashMap<>();
        callback.put("type", "someType");
        CallBackEvent callBackEvent = new CallBackEvent();
        when(eventMappingService.mapEvent(callback)).thenReturn(callBackEvent);
        String expectedResponse = "Success";
        when(callBackService.processCallBack(callBackEvent)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<?> response = vkController.getMessage(callback);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void getMessage_FirstCallThrowsVkApiException_SecondCallSucceeds_ReturnsOk() throws JsonProcessingException, VkApiException, UnknownEventTypeException {
        // Arrange
        Map<String, Object> callback = new HashMap<>();
        callback.put("type", "someType");
        CallBackEvent callBackEvent = new CallBackEvent();
        when(eventMappingService.mapEvent(callback)).thenReturn(callBackEvent);
        when(callBackService.processCallBack(callBackEvent))
                .thenThrow(new VkApiException("API Error"))
                .thenReturn("ok");

        // Act
        ResponseEntity<?> response = vkController.getMessage(callback);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(callBackService, times(2)).processCallBack(callBackEvent);
    }


    @Test
    void getMessage_MissingType_ReturnsBadRequest() throws JsonProcessingException {
        // Arrange
        Map<String, Object> callback = new HashMap<>();

        // Act
        ResponseEntity<?> response = vkController.getMessage(callback);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


    @Test
    void getMessage_ThrowsVkApiException_RetriesAndReturnsServiceUnavailable() throws JsonProcessingException, VkApiException, UnknownEventTypeException {
        // Arrange
        Map<String, Object> callback = new HashMap<>();
        callback.put("type", "someType");
        CallBackEvent callBackEvent = new CallBackEvent();
        when(eventMappingService.mapEvent(callback)).thenReturn(callBackEvent);
        doThrow(new VkApiException("API Error")).when(callBackService).processCallBack(callBackEvent);

        // Act
        ResponseEntity<?> response = vkController.getMessage(callback);

        // Assert
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
        verify(callBackService, times(4)).processCallBack(callBackEvent);
    }

    @Test
    void getMessage_UnknownEventTypeException_Returns400() throws JsonProcessingException, UnknownEventTypeException {
        // Arrange
        Map<String, Object> callback = new HashMap<>();
        callback.put("type", "someType");
        when(eventMappingService.mapEvent(callback)).thenThrow(new UnknownEventTypeException("Unknown type"));

        // Act
        ResponseEntity<?> response = vkController.getMessage(callback);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


    @Test
    void getMessage_UnhandledException_Returns500() throws JsonProcessingException, UnknownEventTypeException {
        // Arrange
        Map<String, Object> callback = new HashMap<>();
        callback.put("type", "someType");
        doThrow(new RuntimeException("Unexpected error")).when(eventMappingService).mapEvent(any());

        // Act
        ResponseEntity<?> response = vkController.getMessage(callback);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
