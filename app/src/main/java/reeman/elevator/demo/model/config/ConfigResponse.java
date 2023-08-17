package reeman.elevator.demo.model.config;

import java.io.Serializable;

public class ConfigResponse implements Serializable {

    private ConfigData data;
    private String requestId;
    private String callType;
    private String buildingId;
    private String groupId;

    public void setData(ConfigData data) {
        this.data = data;
    }
    public ConfigData getData() {
        return data;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
    public String getRequestId() {
        return requestId;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }
    public String getCallType() {
        return callType;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }
    public String getBuildingId() {
        return buildingId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
    public String getGroupId() {
        return groupId;
    }

}
