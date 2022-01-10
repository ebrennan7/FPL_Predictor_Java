package com.example.ember.FPL_Predictor_Java.utilities;

public final class Constants {

    private Constants() {}

    public static final int GOALKEEPER = 1;
    public static final int MAX_GOALKEEPER = 1;
    public static final int MIN_GOALKEEPER = 1;
    public static final int DEFENDER = 2;
    public static final int MAX_DEFENDER = 5;
    public static final int MIN_DEFENDER = 3;
    public static final int MIDFIELDER = 3;
    public static final int MAX_MIDFIELDER = 5;
    public static final int MIN_MIDFIELDER = 2;
    public static final int STRIKER = 4;
    public static final int MAX_STRIKER = 3;
    public static final int MIN_STRIKER = 1;

    public static final int TEAM_SIZE = 11;
    public static final int MAX_TEAM_MEMBERS = 3;
    public static final int TOTAL_PL_TEAMS = 20;
    public static final int MAX_PRICE = 100;

    public static final String UNDERLOADED = "UNDERLOADED";
    public static final String OVERLOADED = "OVERLOADED";
    public static final String AT_MIN = "AT_MIN";
    public static final String HAS_ROOM = "HAS_ROOM";
    public static final String OK = "OK";

    public static final String BOOTSTRAP_URL = "https://fantasy.premierleague.com/api/bootstrap-static/";
    public static final String USER_TEAM_URL_PREFIX = "https://fantasy.premierleague.com/api/entry/";
    public static final String USER_TEAM_URL_EVENT_SUFFIX = "/event/";
    public static final String USER_TEAM_URL_PICKS_SUFFIX = "/picks/";







}