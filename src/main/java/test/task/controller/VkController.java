package test.task.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.task.events.CallBackEvent;
import test.task.exception.UnknownEventTypeException;
import test.task.exception.VkApiException;
import test.task.response.backend.ErrorResponse;
import test.task.service.vk.CallBackService;
import test.task.service.vk.EventMappingService;

import java.util.Map;

@RestController
@RequestMapping("api/vk")

public class VkController {

    private final EventMappingService eventMappingService;
    private final CallBackService callBackService;
    private static final Logger logger = LoggerFactory.getLogger(VkController.class);

    private final int countRetryAttempt = 3;


    public VkController(EventMappingService eventMappingService, CallBackService callBackService) {
        this.eventMappingService = eventMappingService;
        this.callBackService = callBackService;
    }


    @PostMapping("callback")
    public ResponseEntity<?> getMessage(@RequestBody Map<String, Object> callback) throws JsonProcessingException {

        CallBackEvent callBackEvent = null;
        try {
            logger.info("Получен callback: {}", callback);

            if (!callback.containsKey("type")) {
                logger.warn("Callback не содержит ключ 'type': {}", callback);
                return ResponseEntity
                        .badRequest()
                        .body(new ErrorResponse("Неверный формат callback: отсутствует ключ 'type'", HttpStatus.BAD_REQUEST.value()));
            }

            String type = (String) callback.get("type");
            long randomId = System.currentTimeMillis();

            logger.debug("Генерируем random_id: {}", randomId);

            // Обработка события
            callBackEvent = eventMappingService.mapEvent(callback);

            logger.info("Событие успешно преобразовано: {}", callBackEvent);

            var res = callBackService.processCallBack(callBackEvent);
            logger.info("Событие успешно обработано, результат: {}", res);

            return ResponseEntity.ok().body(res);

        } catch (VkApiException e) {
            logger.error("Ошибка вызова VK API, начинаем повторные попытки: {}", e.getMessage(), e);

            for (int i = 0; i < countRetryAttempt; i++) {
                try {
                    logger.debug("Попытка {}:", i + 1);

                    var res = callBackService.processCallBack(callBackEvent);

                    logger.info("Повторный вызов успешен на попытке {}: {}", i + 1, res);
                    return ResponseEntity.ok().body(res);

                } catch (VkApiException retryEx) {
                    logger.warn("Ошибка на попытке {}: {}", i + 1, retryEx.getMessage(), retryEx);

                    if (i == 2) {
                        logger.error("Все попытки вызова VK API исчерпаны.");
                        return ResponseEntity
                                .status(HttpStatus.SERVICE_UNAVAILABLE)
                                .body(new ErrorResponse("Ошибка VK API: " + retryEx.getMessage(), HttpStatus.SERVICE_UNAVAILABLE.value()));
                    }
                }
            }

        } catch (JsonProcessingException e) {
            logger.error("Ошибка обработки JSON: {}", e.getMessage(), e);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Ошибка обработки JSON: " + e.getMessage(), HttpStatus.BAD_REQUEST.value()));

        } catch (UnknownEventTypeException e) {
            logger.error("Неизвестный тип callback", e.getMessage(), e);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Неизвестный тип callback: " + e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        } catch (Exception e) {
            logger.error("Непредвиденная ошибка: {}", e.getMessage(), e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Произошла непредвиденная ошибка: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }

        logger.error("Произошла неизвестная ошибка, возврат по умолчанию.");
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Произошла неизвестная ошибка", HttpStatus.INTERNAL_SERVER_ERROR.value()));

    }

}
