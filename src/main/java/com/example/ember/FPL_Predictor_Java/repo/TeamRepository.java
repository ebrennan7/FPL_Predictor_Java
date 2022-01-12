package com.example.ember.FPL_Predictor_Java.repo;

import com.example.ember.FPL_Predictor_Java.entity.Pick;
import com.example.ember.FPL_Predictor_Java.entity.Player;
import com.example.ember.FPL_Predictor_Java.entity.Response;
import com.example.ember.FPL_Predictor_Java.utilities.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Repository
public class TeamRepository {


    @Autowired
    public TeamRepository() {
    }

    RestTemplate restTemplate = new RestTemplate();

    public List<Pick> getUserTeam(String userId, String gameweek){
        StringBuilder url = new StringBuilder();
        url.append(Constants.USER_TEAM_URL_PREFIX + userId + Constants.USER_TEAM_URL_EVENT_SUFFIX + gameweek + Constants.USER_TEAM_URL_PICKS_SUFFIX);
        Response response = restTemplate.getForObject(url.toString(), Response.class);
        return response.getPicks();
    }

    public int getUserBank(String userId, String gameweek){
        StringBuilder url = new StringBuilder();
        url.append(Constants.USER_TEAM_URL_PREFIX + userId + Constants.USER_TEAM_URL_EVENT_SUFFIX + gameweek + Constants.USER_TEAM_URL_PICKS_SUFFIX);
        Response response = restTemplate.getForObject(url.toString(), Response.class);
        return response.getBank();
    }
}
