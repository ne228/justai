package test.task.service.vk;

import com.fasterxml.jackson.core.JsonProcessingException;
import test.task.exception.VkApiException;
import test.task.request.GetCallbackConfirmationCodeRequest;
import test.task.request.SendMessageRequest;


public interface VkService {

    String sendMessage(SendMessageRequest request) throws JsonProcessingException, VkApiException;

    String getCallbackConfirmationCode(GetCallbackConfirmationCodeRequest request) throws JsonProcessingException, VkApiException;

}
