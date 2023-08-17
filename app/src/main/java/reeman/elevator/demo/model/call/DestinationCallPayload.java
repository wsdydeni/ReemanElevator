package reeman.elevator.demo.model.call;

public class DestinationCallPayload {
    private int request_id;
    private int area;
    private String time;
    private int terminal;
    private DestinationCall call;

    public void setRequestId(int requestId) {
        this.request_id = requestId;
    }
    public int getRequestId() {
        return request_id;
    }

    public void setArea(int area) {
        this.area = area;
    }
    public int getArea() {
        return area;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public String getTime() {
        return time;
    }

    public void setTerminal(int terminal) {
        this.terminal = terminal;
    }
    public int getTerminal() {
        return terminal;
    }

    public void setCall(DestinationCall call) {
        this.call = call;
    }
    public DestinationCall getCall() {
        return call;
    }

    public DestinationCallPayload(int requestId, int area, String time, int terminal, DestinationCall call) {
        this.request_id = requestId;
        this.area = area;
        this.time = time;
        this.terminal = terminal;
        this.call = call;
    }

    @Override
    public String toString() {
        return "DestinationCallPayload{" +
                "request_id=" + request_id +
                ", area=" + area +
                ", time='" + time + '\'' +
                ", terminal=" + terminal +
                ", call=" + call +
                '}';
    }
}
