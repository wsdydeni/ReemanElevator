package reeman.elevator.demo.model.monitor;

import com.google.gson.annotations.SerializedName;

public class MonitorResponse {
    private String subtopic;
    @SerializedName("requestId")
    private String requestId;
    @SerializedName("callType")
    private String callType;
    @SerializedName("buildingId")
    private String buildingId;
    @SerializedName("groupId")
    private String groupId;

    public void setSubtopic(String subtopic) {
        this.subtopic = subtopic;
    }
    public String getSubtopic() {
        return subtopic;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
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
