package br.eti.tmc.ufoviewer.entity;

/**
 * Created by tessomc on 18/01/17.
 */
public class State {

    private Boolean powered;
    private String nextSunrise;
    private String nextSunset;
    private EventType nextEvent;
    private String currentTime;

    public Boolean getPowered() {
        return powered;
    }

    public void setPowered(Boolean powered) {
        this.powered = powered;
    }

    public String getNextSunrise() {
        return nextSunrise;
    }

    public void setNextSunrise(String nextSunrise) {
        this.nextSunrise = nextSunrise;
    }

    public String getNextSunset() {
        return nextSunset;
    }

    public void setNextSunset(String nextSunset) {
        this.nextSunset = nextSunset;
    }

    public EventType getNextEvent() {
        return nextEvent;
    }

    public void setNextEvent(EventType nextEvent) {
        this.nextEvent = nextEvent;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }
}
