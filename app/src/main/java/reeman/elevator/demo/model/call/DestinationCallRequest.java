package reeman.elevator.demo.model.call;

public class DestinationCallRequest {
    private String type;
    private String buildingId;
    private String callType;
    private String groupId;
    private DestinationCallPayload payload;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public DestinationCallPayload getPayload() {
        return payload;
    }

    public void setPayload(DestinationCallPayload payload) {
        this.payload = payload;
    }

    public DestinationCallRequest(String type, String buildingId, String callType, String groupId, DestinationCallPayload payload) {
        this.type = type;
        this.buildingId = buildingId;
        this.callType = callType;
        this.groupId = groupId;
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "DestinationCallRequest{" +
                "type='" + type + '\'' +
                ", buildingId='" + buildingId + '\'' +
                ", callType='" + callType + '\'' +
                ", groupId='" + groupId + '\'' +
                ", payload=" + payload +
                '}';
    }
}
