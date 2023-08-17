package reeman.elevator.demo.model;

public class ConfigOrActionsRequest {

    private String type;
    private String requestId;
    private String buildingId;
    private String callType;
    private String groupId;

    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
    public String getRequestId() {
        return requestId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }
    public String getBuildingId() {
        return buildingId;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }
    public String getCallType() {
        return callType;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
    }

    public ConfigOrActionsRequest(String type, String requestId, String buildingId, String callType, String groupId) {
        this.type = type;
        this.requestId = requestId;
        this.buildingId = buildingId;
        this.callType = callType;
        this.groupId = groupId;
    }
}
