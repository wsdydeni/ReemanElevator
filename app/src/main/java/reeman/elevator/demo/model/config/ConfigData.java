package reeman.elevator.demo.model.config;

import java.io.Serializable;
import java.util.List;

public class ConfigData implements Serializable {

    private int version_major;
    private int version_minor;
    private List<Destinations> destinations;
    private List<Groups> groups;

    public void setVersion_major(int version_major) {
        this.version_major = version_major;
    }
    public int getVersion_major() {
        return version_major;
    }

    public void setVersion_minor(int version_minor) {
        this.version_minor = version_minor;
    }
    public int getVersion_minor() {
        return version_minor;
    }

    public void setDestinations(List<Destinations> destinations) {
        this.destinations = destinations;
    }
    public List<Destinations> getDestinations() {
        return destinations;
    }

    public void setGroups(List<Groups> groups) {
        this.groups = groups;
    }
    public List<Groups> getGroups() {
        return groups;
    }


}
