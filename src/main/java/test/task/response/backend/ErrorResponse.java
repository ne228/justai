package test.task.response.backend;

public class ErrorResponse {
    private String error;
    private int status;
    private String timestamp;

    // Конструктор
    public ErrorResponse(String error, int status) {
        this.error = error;
        this.status = status;
        this.timestamp = java.time.ZonedDateTime.now().toString();
    }

    // Геттеры и сеттеры
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "error='" + error + '\'' +
                ", status=" + status +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
