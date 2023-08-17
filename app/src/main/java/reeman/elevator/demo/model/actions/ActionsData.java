package reeman.elevator.demo.model.actions;

import java.io.Serializable;
import java.util.List;

public class ActionsData implements Serializable {
    private Integer	version_major;
    private Integer	version_minor;
    private List<String> action_set;
    private List<CallTypes>	call_types;
    private List<ActionFunctions> functions;

    public Integer getVersion_major() {
        return this.version_major;
    }

    public void setVersion_major(Integer version_major) {
        this.version_major = version_major;
    }

    public Integer getVersion_minor() {
        return this.version_minor;
    }

    public void setVersion_minor(Integer version_minor) {
        this.version_minor = version_minor;
    }

    public List<String> getAction_set() {
        return this.action_set;
    }

    public void setAction_set(List<String> action_set) {
        this.action_set = action_set;
    }

    public List<CallTypes> getCall_types() {
        return this.call_types;
    }

    public void setCall_types(List<CallTypes> call_types) {
        this.call_types = call_types;
    }

    public List<ActionFunctions> getFunctions() {
        return this.functions;
    }

    public void setFunctions(List<ActionFunctions> functions) {
        this.functions = functions;
    }
}
