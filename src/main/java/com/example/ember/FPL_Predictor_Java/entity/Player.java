package com.example.ember.FPL_Predictor_Java.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Player implements Serializable {

    private Long code;
    private String firstName;
    private String secondName;

    private Long gwPoints;
    private Long position;
    private Long price;
    private String expectedPoints;
    private Long team;

    public Player(Long code, String firstName, String secondName, Long gwPoints, Long position, Long price, String expectedPoints, Long team){
        this.code = code;
        this.firstName=firstName;
        this.secondName=secondName;
        this.gwPoints=gwPoints;
        this.position=position;
        this.price=price;
        this.expectedPoints=expectedPoints;
        this.team=team;
    }

    protected Player(){

    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("first_name")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    @JsonProperty("second_name")
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Long getGwPoints() {
        return gwPoints;
    }

    @JsonProperty("event_points")
    public void setGwPoints(Long gwPoints) {
        this.gwPoints = gwPoints;
    }

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }

    public Long getPrice() {
        return price;
    }

    @JsonProperty("now_cost")
    public void setPrice(Long price) {
        this.price = price / 10;
    }

    public String getExpectedPoints() {
        return expectedPoints;
    }

    @JsonProperty("ep_next")
    public void setExpectedPoints(String expectedPoints) {
        this.expectedPoints = expectedPoints;
    }

    public Long getTeam() {
        return team;
    }

    public void setTeam(Long team) {
        this.team = team;
    }
}
