package com.example.ember.FPL_Predictor_Java.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Response implements Serializable {
    public List<Player> getElements() {
        return elements;
    }

    public void setElements(List<Player> elements) {
        this.elements = elements;
    }

    @JsonUnwrapped
    public List<Player> elements;
}
