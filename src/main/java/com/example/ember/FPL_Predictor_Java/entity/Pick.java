package com.example.ember.FPL_Predictor_Java.entity;

public class Pick {
    private int element;
    private int position;
    private int multiplier;
    private Boolean isCaptain;
    private Boolean isViceCaptain;

    public int getElement() {
        return element;
    }

    public void setElement(int element) {
        this.element = element;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }

    public Boolean getCaptain() {
        return isCaptain;
    }

    public void setCaptain(Boolean captain) {
        isCaptain = captain;
    }

    public Boolean getViceCaptain() {
        return isViceCaptain;
    }

    public void setViceCaptain(Boolean viceCaptain) {
        isViceCaptain = viceCaptain;
    }
}
