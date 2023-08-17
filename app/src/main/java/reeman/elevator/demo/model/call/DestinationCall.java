package reeman.elevator.demo.model.call;

public class DestinationCall {
    private int action;
    private int destination;
    public void setAction(int action) {
        this.action = action;
    }
    public int getAction() {
        return action;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }
    public int getDestination() {
        return destination;
    }

    public DestinationCall(int action, int destination) {
        this.action = action;
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "DestinationCall{" +
                "action=" + action +
                ", destination=" + destination +
                '}';
    }
}
