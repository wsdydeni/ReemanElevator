package reeman.elevator.demo.model.actions;

import java.io.Serializable;

public class ActionFunctions implements Serializable {
    private Integer	action_id;
    private String	action_type;
    private String	enabled;
    private String	name;

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

    public String getEnabled() {
        return this.enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
