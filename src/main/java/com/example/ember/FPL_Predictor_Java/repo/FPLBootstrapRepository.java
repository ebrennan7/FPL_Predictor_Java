package com.example.ember.FPL_Predictor_Java.repo;

import com.example.ember.FPL_Predictor_Java.entity.Gameweek;
import com.example.ember.FPL_Predictor_Java.entity.Player;
import com.example.ember.FPL_Predictor_Java.entity.Response;
import com.example.ember.FPL_Predictor_Java.service.TeamBuilderService;
import com.example.ember.FPL_Predictor_Java.utilities.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Repository
public class FPLBootstrapRepository {


    @Autowired
    public FPLBootstrapRepository() {
    }

    RestTemplate restTemplate = new RestTemplate();

    public List<Player> getAllPlayers(){
        Response response = restTemplate.getForObject(Constants.BOOTSTRAP_URL, Response.class);
        return response.getElements();
    }

    public List<Gameweek> getGameweeks(){
        Response response = restTemplate.getForObject(Constants.BOOTSTRAP_URL, Response.class);
        return response.getEvents();
    }
}
