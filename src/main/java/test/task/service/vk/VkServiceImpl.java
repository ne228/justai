package test.task.service.vk;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import test.task.exception.VkApiException;
import test.task.request.GetCallbackConfirmationCodeRequest;
import test.task.request.SendMessageRequest;
import test.task.response.ConfirmationCodeResponse;
import test.task.response.SendMessageResponse;
import test.task.service.utils.HttpService;


@Service
public class VkServiceImpl implements VkService {
    private final HttpService httpService;
    @Value("${vk.api.url}")
    private String vkUrl;


    public VkServiceImpl(HttpService httpService) {
        this.httpService = httpService;
    }

    @Override
    public String sendMessage(SendMessageRequest sendMessageRequest) throws JsonProcessingException, VkApiException {
        var url = vkUrl + "/method/messages.send";
        var response = httpService.sendPostRequest(url, sendMessageRequest, SendMessageResponse.class);

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

        if (response.getBody().getError() != null) {
            throw new VkApiException(String.format("Ошибка при получения кода подтвержденя. Request: %s. Response: %s",
                    request.toString(),
                    response.getBody().getError().toString()));
        }
        return response.getBody().getCode();
    }
}
