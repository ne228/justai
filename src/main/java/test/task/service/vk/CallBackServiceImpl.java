package test.task.service.vk;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import test.task.events.CallBackEvent;
import test.task.events.ConfirmationEvent;
import test.task.events.NewMessageEvent;
import test.task.exception.VkApiException;
import test.task.request.GetCallbackConfirmationCodeRequest;
import test.task.request.SendMessageRequest;

import java.util.logging.Logger;


@Service

public class CallBackServiceImpl implements CallBackService {

    @Value("${vk.api.callback.secret}")
    private String secret;

    private final VkService vkService;

    public CallBackServiceImpl(VkService vkService) {
        this.vkService = vkService;
    }

    private static final Logger logger = Logger.getLogger(CallBackServiceImpl.class.getName());

    @Override
    public String processCallBack(CallBackEvent callback) throws JsonProcessingException, VkApiException {

        if (callback == null || !callback.getSecret().equals(secret)) {
            throw new SecurityException("Неверный secret callBack");
        }

        // В зависимости от события своершаем действия
        if (callback instanceof ConfirmationEvent confirmationEvent) {
            var request = new GetCallbackConfirmationCodeRequest(confirmationEvent.getGroupId());
            logger.info("ConfirmationEvent: " + request.toString());
            return vkService.getCallbackConfirmationCode(request);
        }

        if (callback instanceof NewMessageEvent newMessageCallBack) {
            var request = new SendMessageRequest(
                    newMessageCallBack.getObject()
                            .getMessage()
                            .getFromId(),
                    "Вы сказали: " + newMessageCallBack.getObject()
                            .getMessage()
                            .getText()
            );
            logger.info("NewMessageEvent: " + request.toString());
            var response = vkService.sendMessage(request);
            return "ok";
        }


        throw new IllegalArgumentException("Неизвестный тип события");
    }
}
