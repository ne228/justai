package test.task.service.vk;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import test.task.events.CallBackEvent;
import test.task.events.ConfirmationEvent;
import test.task.events.NewMessageEvent;
import test.task.exception.UnknownEventTypeException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventMappingServiceImplTest {

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private EventMappingServiceImpl eventMappingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMapEvent_MessageNew() throws UnknownEventTypeException {
        // Arrange
        Map<String, Object> eventPayload = Map.of("type", "message_new", "message", "test message");
        NewMessageEvent newMessageEvent = new NewMessageEvent();
        when(objectMapper.convertValue(eventPayload, NewMessageEvent.class)).thenReturn(newMessageEvent);

        // Act
        CallBackEvent result = eventMappingService.mapEvent(eventPayload);

        // Assert
        assertTrue(result instanceof NewMessageEvent);
        verify(objectMapper, times(1)).convertValue(eventPayload, NewMessageEvent.class);
    }

    @Test
    void testMapEvent_Confirmation() throws UnknownEventTypeException {
        // Arrange
        Map<String, Object> eventPayload = Map.of("type", "confirmation", "code", "12345");
        ConfirmationEvent confirmationEvent = new ConfirmationEvent();
        when(objectMapper.convertValue(eventPayload, ConfirmationEvent.class)).thenReturn(confirmationEvent);

        // Act
        CallBackEvent result = eventMappingService.mapEvent(eventPayload);

        // Assert
        assertTrue(result instanceof ConfirmationEvent);
        verify(objectMapper, times(1)).convertValue(eventPayload, ConfirmationEvent.class);
    }

    @Test
    void testMapEvent_UnknownType() {
        // Arrange
        Map<String, Object> eventPayload = Map.of("type", "unknown_event");

        // Act & Assert
        UnknownEventTypeException exception = assertThrows(UnknownEventTypeException.class, () -> eventMappingService.mapEvent(eventPayload));
        assertEquals("Неизвестный тип события: unknown_event", exception.getMessage());
    }

    @Test
    void testMapEvent_WithExtraFields() throws UnknownEventTypeException {
        // Arrange
        Map<String, Object> eventPayload = Map.of("type", "message_new", "message", "test message", "extraField", "extraValue");
        NewMessageEvent newMessageEvent = new NewMessageEvent();
        when(objectMapper.convertValue(eventPayload, NewMessageEvent.class)).thenReturn(newMessageEvent);

        // Act
        CallBackEvent result = eventMappingService.mapEvent(eventPayload);

        // Assert
        assertTrue(result instanceof NewMessageEvent);
        verify(objectMapper, times(1)).convertValue(eventPayload, NewMessageEvent.class);
    }
}
