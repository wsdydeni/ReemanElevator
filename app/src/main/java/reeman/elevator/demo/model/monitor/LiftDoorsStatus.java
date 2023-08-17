package reeman.elevator.demo.model.monitor;

public class LiftDoorsStatus {
    private String time;
    private int area;
    private int lift_side;
    private String state;
    private int landing;
    public void setTime(String time) {
        this.time = time;
    }
    public String getTime() {
        return time;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public int getArea() {
        return area;
    }

    public void setLiftSide(int liftSide) {
        this.lift_side = liftSide;
    }
    public int getLiftSide() {
        return lift_side;
    }

    public void setState(String state) {
        this.state = state;
    }
    public String getState() {
        return state;
    }

    public void setLanding(int landing) {
        this.landing = landing;
    }
    public int getLanding() {
        return landing;
    }
}
