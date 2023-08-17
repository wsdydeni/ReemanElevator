package reeman.elevator.demo.model.config;

import java.io.Serializable;
import java.util.List;

public class Sides implements Serializable {

    private List<Integer> decks;
    private int group_side;
    private int lift_side;
    public void setDecks(List<Integer> decks) {
        this.decks = decks;
    }
    public List<Integer> getDecks() {
        return decks;
    }

    public void setGroup_side(int group_side) {
        this.group_side = group_side;
    }
    public int getGroup_side() {
        return group_side;
    }

    public void setLift_side(int lift_side) {
        this.lift_side = lift_side;
    }
    public int getLift_side() {
        return lift_side;
    }

}
