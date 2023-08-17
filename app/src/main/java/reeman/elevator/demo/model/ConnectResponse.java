package reeman.elevator.demo.model;

public class ConnectResponse {
    private String connectionId;
    private String requestId;
    private int statusCode;
    private TimeData data;
    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }
    public String getConnectionId() {
        return connectionId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
    public String getRequestId() {
        return requestId;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
    public int getStatusCode() {
        return statusCode;
    }

    public void setData(TimeData data) {
        this.data = data;
    }
    public TimeData getData() {
        return data;
    }

}
