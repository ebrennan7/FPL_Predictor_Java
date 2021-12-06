package com.example.ember.FPL_Predictor_Java.service;

import com.example.ember.FPL_Predictor_Java.entity.Player;
import com.example.ember.FPL_Predictor_Java.entity.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class FPLBootstrapService {

    @Autowired
    public FPLBootstrapService() {
    }


    HttpHeaders headers = new HttpHeaders();
    HttpEntity<String> request = new HttpEntity<String>(headers);
    RestTemplate restTemplate = new RestTemplate();


    public List<Player> getAllPlayers(){
        Response response = restTemplate.getForObject("https://fantasy.premierleague.com/api/bootstrap-static/", Response.class);
        return response.getElements();
    }

    public Player createPlayer(Long code, String firstName, String secondName, Long gwPoints,
                           Long position, Long price,
                           String expectedPoints, Long team) {

        return new Player(code, firstName,secondName, gwPoints, position,
                price, expectedPoints, team);
    }

}
