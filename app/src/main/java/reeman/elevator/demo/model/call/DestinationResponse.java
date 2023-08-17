package reeman.elevator.demo.model.call;

public class DestinationResponse {
    DestinationResponseData data;
    String callType;
    String buildingId;
    String groupId;

    public DestinationResponseData getData() {
        return data;
    }

    public void setData(DestinationResponseData data) {
        this.data = data;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
