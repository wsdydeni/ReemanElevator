package reeman.elevator.demo.model.resources;

import java.io.Serializable;
import java.util.List;

public class BuildingResult implements Serializable {
    private List<Buildings> buildings;
    private List<Equipments> equipments;
    public void setBuildings(List<Buildings> buildings) {
        this.buildings = buildings;
    }
    public List<Buildings> getBuildings() {
        return buildings;
    }

    public void setEquipments(List<Equipments> equipments) {
        this.equipments = equipments;
    }
    public List<Equipments> getEquipments() {
        return equipments;
    }
}
