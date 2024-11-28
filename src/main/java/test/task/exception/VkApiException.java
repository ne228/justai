package test.task.exception;

public class VkApiException extends Exception {
    public VkApiException(String message) {
        super(message);
    }
    public VkApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
