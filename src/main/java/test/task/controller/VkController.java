package test.task.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.task.exception.VkApiException;
import test.task.service.vk.CallBackService;
import test.task.service.vk.EventMappingService;

import java.util.Map;

@RestController
@RequestMapping("api/vk")

public class VkController {

    private final EventMappingService eventMappingService;
    private final CallBackService callBackService;

    public VkController(EventMappingService eventMappingService, CallBackService callBackService) {
        this.eventMappingService = eventMappingService;
        this.callBackService = callBackService;
    }


    @PostMapping("callback")
    public ResponseEntity<?> getMessage(@RequestBody Map<String, Object> callback) throws JsonProcessingException, VkApiException {
        if (!callback.containsKey("type"))
            throw new IllegalArgumentException("Неверный формат callback");

        String type = (String) callback.get("type");
        // Получаем объект event
        var callBackEvent = eventMappingService.mapEvent(callback);

        // Обработка события
        var res = callBackService.processCallBack(callBackEvent);

        return ResponseEntity.ok().body(res);
    }


}
