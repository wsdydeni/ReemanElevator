package reeman.elevator.demo.model.resources;

import java.io.Serializable;
import java.util.List;

public class Buildings implements Serializable {

    private String id;
    private String name;
    private String desc;
    private List<Groups> groups;
    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getDesc() {
        return desc;
    }

    public void setGroups(List<Groups> groups) {
        this.groups = groups;
    }
    public List<Groups> getGroups() {
        return groups;
    }

}
