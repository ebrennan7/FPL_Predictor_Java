package com.example.ember.FPL_Predictor_Java.service;

import com.example.ember.FPL_Predictor_Java.entity.Player;
import com.example.ember.FPL_Predictor_Java.entity.Response;
import com.example.ember.FPL_Predictor_Java.utilities.Constants;
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

    private List<Player> getPointsSortedPlayers(){
        List<Player> allPlayers = getAllPlayers();
        allPlayers.sort(Comparator.comparing(Player::getGwPoints).reversed());

        return allPlayers;
    }

    private List<Player> getExpectedPointsSortedPlayers(){
        List<Player> allPlayers = getAllPlayers();
        allPlayers.sort(Comparator.comparing(Player::getExpectedPoints).reversed());

        return allPlayers;
    }

    public List<Player> getDreamTeam(){
        List<Player> sortedPlayers = getAllPlayers();
        List<Player> dreamTeam = new ArrayList<>();

        sortedPlayers.forEach(player ->{
            if(player.getInDreamTeam()){
                dreamTeam.add(player);
            }
        });

        dreamTeam.sort(Comparator.comparing(Player::getPosition));
        return dreamTeam;
    }

    public List<Player> getKingsOfGameweek(){
        List<Player> bestPlayers = getPointsSortedPlayers();
        List<Player> wildcardTeam = new ArrayList<>();

        for(int i=0;i< Constants.TEAM_SIZE;i++){
            wildcardTeam.add(bestPlayers.get(0));
            bestPlayers.remove(bestPlayers.get(0));
        }

        bestPlayers.sort(Comparator.comparing(Player::getGwPoints).reversed().thenComparing(Player::getPrice));

        TeamBuilderService teamBuilderService = new TeamBuilderService(wildcardTeam, bestPlayers);

        wildcardTeam = teamBuilderService.buildTeam();
        wildcardTeam.sort(Comparator.comparing(Player::getPosition));

        return wildcardTeam;
    }

    public List<Player> getWildcardTeam(){
        List<Player> bestPlayers = getExpectedPointsSortedPlayers();
        List<Player> wildcardTeam = new ArrayList<>();

        for(int i=0;i< Constants.TEAM_SIZE;i++){
            wildcardTeam.add(bestPlayers.get(0));
            bestPlayers.remove(bestPlayers.get(0));
        }

        bestPlayers.sort(Comparator.comparing(Player::getExpectedPoints).reversed().thenComparing(Player::getPrice));

        TeamBuilderService teamBuilderService = new TeamBuilderService(wildcardTeam, bestPlayers, true);

        wildcardTeam = teamBuilderService.buildTeam();
        wildcardTeam.sort(Comparator.comparing(Player::getPosition));

        return wildcardTeam;

    }

}
