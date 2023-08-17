package reeman.elevator.demo.model.config;

import java.io.Serializable;
import java.util.List;

public class Lifts implements Serializable {

    private int lift_id;
    private String lift_name;
    private List<Decks> decks;
    private List<Floors> floors;

    public void setLift_id(int lift_id) {
        this.lift_id = lift_id;
    }
    public int getLift_id() {
        return lift_id;
    }

    public void setLift_name(String lift_name) {
        this.lift_name = lift_name;
    }
    public String getLift_name() {
        return lift_name;
    }

    public void setDecks(List<Decks> decks) {
        this.decks = decks;
    }
    public List<Decks> getDecks() {
        return decks;
    }

    public void setFloors(List<Floors> floors) {
        this.floors = floors;
    }
    public List<Floors> getFloors() {
        return floors;
    }

}
