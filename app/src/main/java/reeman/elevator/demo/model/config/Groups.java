package reeman.elevator.demo.model.config;

import java.io.Serializable;
import java.util.List;

public class Groups implements Serializable {

    private int group_id;
    private List<Integer> terminals;
    private List<Lifts> lifts;

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }
    public int getGroup_id() {
        return group_id;
    }

    public void setTerminals(List<Integer> terminals) {
        this.terminals = terminals;
    }
    public List<Integer> getTerminals() {
        return terminals;
    }

    public void setLifts(List<Lifts> lifts) {
        this.lifts = lifts;
    }
    public List<Lifts> getLifts() {
        return lifts;
    }

}
