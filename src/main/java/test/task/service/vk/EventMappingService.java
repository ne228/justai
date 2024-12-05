package test.task.service.vk;

import test.task.events.CallBackEvent;
import test.task.exception.UnknownEventTypeException;

import java.util.Map;

public interface EventMappingService {
    public CallBackEvent mapEvent(Map<String, Object> eventPayload) throws UnknownEventTypeException;
}
