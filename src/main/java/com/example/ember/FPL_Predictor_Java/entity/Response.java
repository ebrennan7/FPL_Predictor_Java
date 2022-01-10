package com.example.ember.FPL_Predictor_Java.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Response implements Serializable {

    @JsonUnwrapped
    public List<Player> elements;

    @JsonUnwrapped
    public List<Gameweek> events;

    @JsonUnwrapped
    public List<Pick> picks;

    public List<Pick> getPicks() {
        return picks;
    }

    public void setPicks(List<Pick> picks) {
        this.picks = picks;
    }

    public List<Player> getElements() {
        return elements;
    }

    public void setElements(List<Player> elements) {
        this.elements = elements;
    }

    public List<Gameweek> getEvents() {
        return events;
    }

    public void setEvents(List<Gameweek> events) {
        this.events = events;
    }

}
