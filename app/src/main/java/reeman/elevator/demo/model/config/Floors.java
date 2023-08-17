package reeman.elevator.demo.model.config;

import java.io.Serializable;
import java.util.List;


public class Floors implements Serializable {

    private int group_floor_id;
    private int height;
    private int lift_floor_id;
    private List<Sides> sides;

    public void setGroup_floor_id(int group_floor_id) {
        this.group_floor_id = group_floor_id;
    }
    public int getGroup_floor_id() {
        return group_floor_id;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    public int getHeight() {
        return height;
    }

    public void setLift_floor_id(int lift_floor_id) {
        this.lift_floor_id = lift_floor_id;
    }
    public int getLift_floor_id() {
        return lift_floor_id;
    }

    public void setSides(List<Sides> sides) {
        this.sides = sides;
    }
    public List<Sides> getSides() {
        return sides;
    }

}
