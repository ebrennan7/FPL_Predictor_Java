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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class FPLBootstrapService {

    List<Player> team;

    @Autowired
    public FPLBootstrapService(List<Player> team) {
        this.team=team;
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

        for(int i=0;i< Constants.TEAM_SIZE;i++){
            team.add(bestPlayers.get(0));
            bestPlayers.remove(bestPlayers.get(0));
        }

        bestPlayers.sort(Comparator.comparing(Player::getGwPoints).reversed().thenComparing(Player::getPrice));

        TeamBuilderService teamBuilderService = new TeamBuilderService(team, bestPlayers);

        team = teamBuilderService.buildTeam();
        team.sort(Comparator.comparing(Player::getPosition));
        getPlayerImages();

        return team;
    }

    public List<Player> getWildcardTeam(){
        List<Player> bestPlayers = getExpectedPointsSortedPlayers();

        for(int i=0;i< Constants.TEAM_SIZE;i++){
            team.add(bestPlayers.get(0));
            bestPlayers.remove(bestPlayers.get(0));
        }

        bestPlayers.sort(Comparator.comparing(Player::getExpectedPoints).reversed().thenComparing(Player::getPrice));

        TeamBuilderService teamBuilderService = new TeamBuilderService(team, bestPlayers, true);

        team = teamBuilderService.buildTeam();
        team.sort(Comparator.comparing(Player::getPosition));
        getPlayerImages();

        return team;

    }

    private void getPlayerImages(){
        for(Player player:team){
            String template = "https://resources.premierleague.com/premierleague/photos/players/110x140/p{0}.png";
            String result = MessageFormat.format(template, String.valueOf(player.getCode()));
            player.setImageURL(result);
        }


    }

}
