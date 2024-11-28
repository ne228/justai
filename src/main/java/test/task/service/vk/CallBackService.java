package test.task.service.vk;

import com.fasterxml.jackson.core.JsonProcessingException;
import test.task.events.CallBackEvent;
import test.task.exception.VkApiException;


public interface CallBackService {

    String processCallBack(CallBackEvent callback) throws JsonProcessingException, VkApiException;
}
