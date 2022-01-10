package com.example.ember.FPL_Predictor_Java.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Player implements Serializable {

    private int code;
    private String firstName;
    private String secondName;

    private int gwPoints;
    private int position;
    private Float price;
    private Float expectedPoints;
    private int team;
    private String imageURL;
    private Float selectedBy;
    private int id;

    public int getId(){ return id; }

    public void setId(int id){
        this.id = id;
    }

    public Float getSelectedBy() { return selectedBy; }

    @JsonProperty("selected_by_percent")
    public void setSelectedBy(String selectedBy){
        this.selectedBy=Float.parseFloat(selectedBy);
    }

    public Boolean getInDreamTeam() {
        return inDreamTeam;
    }

    @JsonProperty("in_dreamteam")
    public void setInDreamTeam(Boolean inDreamTeam) {
        this.inDreamTeam = inDreamTeam;
    }

    private Boolean inDreamTeam;

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

//    public Player(int code, String firstName, String secondName, int gwPoints, int position, Float price, Float expectedPoints, int team){
//        this.code = code;
//        this.firstName=firstName;
//        this.secondName=secondName;
//        this.gwPoints=gwPoints;
//        this.position=position;
//        this.price=price;
//        this.expectedPoints=expectedPoints;
//        this.team=team;
//    }

    protected Player(){

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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

    public int getGwPoints() {
        return gwPoints;
    }

    @JsonProperty("event_points")
    public void setGwPoints(int gwPoints) {
        this.gwPoints = gwPoints;
    }

    public int getPosition() {
        return position;
    }

    @JsonProperty("element_type")
    public void setPosition(int position) {
        this.position = position;
    }

    public Float getPrice() {
        return price;
    }

    @JsonProperty("now_cost")
    public void setPrice(Float price) {
        this.price = price / 10;
    }

    public Float getExpectedPoints() {
        return expectedPoints;
    }

    @JsonProperty("ep_next")
    public void setExpectedPoints(String expectedPoints) {
        this.expectedPoints = Float.parseFloat(expectedPoints);
    }

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }
}
