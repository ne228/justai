package test.task.service.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

public interface HttpService {

    public <T, R> ResponseEntity<R> sendPostRequest(String url, T body, Class<R> responseType) throws JsonProcessingException;


}
