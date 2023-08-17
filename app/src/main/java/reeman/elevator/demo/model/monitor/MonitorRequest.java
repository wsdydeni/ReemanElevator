package reeman.elevator.demo.model.monitor;

public class MonitorRequest {
    private String type;
    private String requestId;
    private String buildingId;
    private String callType;
    private String groupId;
    private MonitorPayload payload;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public MonitorPayload getPayload() {
        return payload;
    }

    public void setPayload(MonitorPayload payload) {
        this.payload = payload;
    }
}
