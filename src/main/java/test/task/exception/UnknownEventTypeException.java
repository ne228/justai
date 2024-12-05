package test.task.exception;


public class UnknownEventTypeException extends Exception {
    public UnknownEventTypeException(String message) {
        super(message);
    }

    public UnknownEventTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
