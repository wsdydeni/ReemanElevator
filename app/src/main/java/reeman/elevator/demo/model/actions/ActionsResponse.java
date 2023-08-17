package reeman.elevator.demo.model.actions;

import java.io.Serializable;

public class ActionsResponse implements Serializable {
    private ActionsData	data;
    private String	requestId;
    private String	callType;
    private String	buildingId;
    private String	groupId;

    public ActionsData getData() {
        return data;
    }

    public void setData(ActionsData data) {
        this.data = data;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getCallType() {
        return this.callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public String getBuildingId() {
        return this.buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
