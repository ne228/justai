package test.task.service.vk;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import test.task.events.CallBackEvent;
import test.task.events.ConfirmationEvent;
import test.task.events.NewMessageEvent;

import java.util.Map;

@Service
public class EventMappingServiceImpl implements EventMappingService {

    private final ObjectMapper objectMapper;

    public EventMappingServiceImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public CallBackEvent mapEvent(Map<String, Object> eventPayload) {
        String eventType = (String) eventPayload.get("type");

        // В зависимости от типа возвращаем нужный класс
        switch (eventType) {
            case "message_new":
                return objectMapper.convertValue(eventPayload, NewMessageEvent.class);
            case "confirmation":
                return objectMapper.convertValue(eventPayload, ConfirmationEvent.class);
            default:
                throw new IllegalArgumentException("Неизвестный тип события: " + eventType);
        }
    }
}
