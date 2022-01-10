package com.example.ember.FPL_Predictor_Java.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Gameweek implements Serializable {

    private int Id;
    private Boolean isCurrent;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Boolean getCurrent() {
        return isCurrent;
    }

    @JsonProperty("is_current")
    public void setCurrent(Boolean current) {
        isCurrent = current;
    }
}
