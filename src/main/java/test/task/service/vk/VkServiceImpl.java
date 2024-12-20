package test.task.service.vk;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import test.task.exception.VkApiException;
import test.task.request.GetCallbackConfirmationCodeRequest;
import test.task.request.SendMessageRequest;
import test.task.response.vkApi.ConfirmationCodeResponse;
import test.task.response.vkApi.SendMessageResponse;
import test.task.service.utils.HttpService;



@Service
public class VkServiceImpl implements VkService {
    private final HttpService httpService;
    @Value("${vk.api.url}")
    private String vkUrl;

    Logger logger = LoggerFactory.getLogger(CallBackServiceImpl.class);


    public VkServiceImpl(HttpService httpService) {
        this.httpService = httpService;
    }

    @Override
    public String sendMessage(SendMessageRequest sendMessageRequest) throws JsonProcessingException, VkApiException {
        var url = vkUrl + "/method/messages.send";
        var response = httpService.sendPostRequest(url, sendMessageRequest, SendMessageResponse.class);
        logger.debug("Send request {}", sendMessageRequest);

        if (response.getBody().getError() != null) {
            throw new VkApiException(String.format("Ошибка при отправке сообщения. Request: %s. Response: %s",
                    sendMessageRequest.toString(),
                    response.getBody().getError().toString()));
        }
        return response.getBody().getResponse();
    }

    @Override
    public String getCallbackConfirmationCode(GetCallbackConfirmationCodeRequest request) throws JsonProcessingException, VkApiException {
        var url = vkUrl + "/method/groups.getCallbackConfirmationCode";
        var response = httpService.sendPostRequest(url, request, ConfirmationCodeResponse.class);
        logger.debug("Send request {}", request);

        if (response.getBody().getError() != null) {
            throw new VkApiException(String.format("Ошибка при получения кода подтвержденя. Request: %s. Response: %s",
                    request.toString(),
                    response.getBody().getError().toString()));
        }
        return response.getBody().getCode();
    }
}
