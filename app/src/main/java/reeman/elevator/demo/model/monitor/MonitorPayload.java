package reeman.elevator.demo.model.monitor;

import java.util.List;

public class MonitorPayload {
    private String sub;
    private int duration;
    private List<String> subtopics;
    public void setSub(String sub) {
        this.sub = sub;
    }
    public String getSub() {
        return sub;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    public int getDuration() {
        return duration;
    }

    public void setSubtopics(List<String> subtopics) {
        this.subtopics = subtopics;
    }
    public List<String> getSubtopics() {
        return subtopics;
    }
}
