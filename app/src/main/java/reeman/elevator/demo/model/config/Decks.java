package reeman.elevator.demo.model.config;

import java.io.Serializable;
import java.util.List;

public class Decks implements Serializable {

    private long area_id;
    private int deck;
    private List<Integer> doors;
    private List<Integer> terminals;
    public void setArea_id(long area_id) {
        this.area_id = area_id;
    }
    public long getArea_id() {
        return area_id;
    }

    public void setDeck(int deck) {
        this.deck = deck;
    }
    public int getDeck() {
        return deck;
    }

    public void setDoors(List<Integer> doors) {
        this.doors = doors;
    }
    public List<Integer> getDoors() {
        return doors;
    }

    public void setTerminals(List<Integer> terminals) {
        this.terminals = terminals;
    }
    public List<Integer> getTerminals() {
        return terminals;
    }

}
