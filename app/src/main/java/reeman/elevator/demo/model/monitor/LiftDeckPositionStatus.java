package reeman.elevator.demo.model.monitor;

import com.google.gson.annotations.SerializedName;

public class LiftDeckPositionStatus {

    private String time;
    private String dir;
    private String coll;
    @SerializedName("moving_state")
    private String movingState;
    private int area;
    private int cur;
    private int adv;
    private boolean door;

    public void setTime(String time) {
        this.time = time;
    }
    public String getTime() {
        return time;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }
    public String getDir() {
        return dir;
    }

    public void setColl(String coll) {
        this.coll = coll;
    }
    public String getColl() {
        return coll;
    }

    public void setMovingState(String movingState) {
        this.movingState = movingState;
    }
    public String getMovingState() {
        return movingState;
    }

    public void setArea(int area) {
        this.area = area;
    }
    public int getArea() {
        return area;
    }

    public void setCur(int cur) {
        this.cur = cur;
    }
    public int getCur() {
        return cur;
    }

    public void setAdv(int adv) {
        this.adv = adv;
    }
    public int getAdv() {
        return adv;
    }

    public void setDoor(boolean door) {
        this.door = door;
    }
    public boolean getDoor() {
        return door;
    }

}
