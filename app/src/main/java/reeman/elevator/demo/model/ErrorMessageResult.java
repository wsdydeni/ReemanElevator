package reeman.elevator.demo.model;

public class ErrorMessageResult {
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorMessageResult{" +
                "message='" + message + '\'' +
                '}';
    }
}
