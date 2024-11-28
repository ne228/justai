package test.task.service.vk;

import test.task.events.CallBackEvent;

import java.util.Map;

public interface EventMappingService {
    public CallBackEvent mapEvent(Map<String, Object> eventPayload);
}
