package reeman.elevator.demo.model.actions;

import java.io.Serializable;

public class CallTypes implements Serializable {
    private Integer	action_id;
    private String	action_type;
    private String	direct_allowed;
    private String	enabled;
    private String	group_call_allowed;
    private Integer	key;
    private String	name;
    private String	preferred_allowed;

    public Integer getAction_id() {
        return this.action_id;
    }

    public void setAction_id(Integer action_id) {
        this.action_id = action_id;
    }

    public String getAction_type() {
        return this.action_type;
    }

    public void setAction_type(String action_type) {
        this.action_type = action_type;
    }

    public String getDirect_allowed() {
        return this.direct_allowed;
    }

    public void setDirect_allowed(String direct_allowed) {
        this.direct_allowed = direct_allowed;
    }

    public String getEnabled() {
        return this.enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getGroup_call_allowed() {
        return this.group_call_allowed;
    }

    public void setGroup_call_allowed(String group_call_allowed) {
        this.group_call_allowed = group_call_allowed;
    }

    public Integer getKey() {
        return this.key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPreferred_allowed() {
        return this.preferred_allowed;
    }

    public void setPreferred_allowed(String preferred_allowed) {
        this.preferred_allowed = preferred_allowed;
    }
}
