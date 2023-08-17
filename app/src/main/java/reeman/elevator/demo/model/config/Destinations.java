package reeman.elevator.demo.model.config;

import java.io.Serializable;

public class Destinations implements Serializable {

    private int area_id;
    private boolean exit;
    private int group_floor_id;
    private int group_side;
    private String short_name;
    public void setArea_id(int area_id) {
        this.area_id = area_id;
    }
    public int getArea_id() {
        return area_id;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }
    public boolean getExit() {
        return exit;
    }

    public void setGroup_floor_id(int group_floor_id) {
        this.group_floor_id = group_floor_id;
    }
    public int getGroup_floor_id() {
        return group_floor_id;
    }

    public void setGroup_side(int group_side) {
        this.group_side = group_side;
    }
    public int getGroup_side() {
        return group_side;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }
    public String getShort_name() {
        return short_name;
    }

}
