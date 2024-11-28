package test.task.service.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class HttpServiceImpl implements HttpService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${auth.token}")
    private String bearerToken;

    public HttpServiceImpl(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public <T, R> ResponseEntity<R> sendPostRequest(String url, T body, Class<R> responseType) throws JsonProcessingException {
        var headers = createHeaders();

        // Преобразуем объект body в Map<String, String>
        var formData = objectMapper.convertValue(body, new TypeReference<Map<String, String>>() {
        });
        var multipartData = new LinkedMultiValueMap<>();
        formData.forEach(multipartData::add);

        var entity = new HttpEntity<>(multipartData, headers);
        return restTemplate.exchange(url, HttpMethod.POST, entity, responseType);
    }


    private HttpHeaders createHeaders() {
        var headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + bearerToken);  // Используем токен из properties
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set(HttpHeaders.ACCEPT_CHARSET, StandardCharsets.UTF_8.name());
        return headers;
    }
}
