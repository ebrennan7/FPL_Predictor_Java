package com.example.ember.FPL_Predictor_Java.service;

import com.example.ember.FPL_Predictor_Java.entity.Gameweek;
import com.example.ember.FPL_Predictor_Java.entity.Player;
import com.example.ember.FPL_Predictor_Java.repo.FPLBootstrapRepository;
import com.example.ember.FPL_Predictor_Java.utilities.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class FPLBootstrapService {

    FPLBootstrapRepository fplBootstrapRepository;

    PlayerImageService playerImageService;

    @Autowired
    public FPLBootstrapService(FPLBootstrapRepository fplBootstrapRepository, PlayerImageService playerImageService){
        this.fplBootstrapRepository=fplBootstrapRepository;
        this.playerImageService=playerImageService;
    }

    public List<Player> getAllPlayers(){
        return playerImageService.getPlayerImages(fplBootstrapRepository.getAllPlayers());
    }


    private List<Player> getPointsSortedPlayers(){
        List<Player> allPlayers = fplBootstrapRepository.getAllPlayers();
        allPlayers.sort(Comparator.comparing(Player::getGwPoints).reversed());

        return allPlayers;
    }

    private List<Player> getExpectedPointsSortedPlayers(){
        List<Player> allPlayers = fplBootstrapRepository.getAllPlayers();
//        allPlayers.sort(Comparator.comparing(Player::getSelectedBy*getExpectedPoints).reversed());

        allPlayers.sort(Comparator.comparing((Player p)->p.getExpectedPoints()*p.getExpectedPoints()).reversed());
        return allPlayers;
    }

    private List<Player> getAlgorithmSortedPlayers(){
        List<Player> allPlayers = fplBootstrapRepository.getAllPlayers();

        for(Player player:allPlayers){
            Float weight = player.getExpectedPoints() * player.getGwPoints() * player.getSelectedBy();
            Float valueForMoney = weight/player.getPrice();
            player.setPerformanceWeight(weight);
            player.setValueForMoney(valueForMoney);
        }
        allPlayers.sort(Comparator.comparing(Player::getPerformanceWeight).reversed());

        return allPlayers;
    }

    public List<Player> getDreamTeam(){
        List<Player> sortedPlayers = fplBootstrapRepository.getAllPlayers();
        List<Player> dreamTeam = new ArrayList<>();

        sortedPlayers.forEach(player ->{
            if(player.getInDreamTeam()){
                dreamTeam.add(player);
            }
        });

        dreamTeam.sort(Comparator.comparing(Player::getPosition));
        return playerImageService.getPlayerImages(dreamTeam);
    }

    public List<Player> getKingsOfGameweek(){
        List<Player> bestPlayers = getPointsSortedPlayers();
        List<Player> kingsOfGameweekTeam = new ArrayList<>();

        for(int i = 0; i< Constants.TEAM_SIZE; i++){
            kingsOfGameweekTeam.add(bestPlayers.get(0));
            bestPlayers.remove(bestPlayers.get(0));
        }

        bestPlayers.sort(Comparator.comparing(Player::getGwPoints).reversed().thenComparing(Player::getPrice));

        TeamBuilderService teamBuilderService = new TeamBuilderService(kingsOfGameweekTeam, bestPlayers);

        kingsOfGameweekTeam = teamBuilderService.buildTeam();
        kingsOfGameweekTeam.sort(Comparator.comparing(Player::getPosition));

        return playerImageService.getPlayerImages(kingsOfGameweekTeam);
    }

    public List<Player> getWildcardTeam(){
        List<Player> bestPlayers = getExpectedPointsSortedPlayers();
        List<Player> wildcardTeam = new ArrayList<>();

        for(int i=0;i< Constants.TEAM_SIZE;i++){
            wildcardTeam.add(bestPlayers.get(0));
            bestPlayers.remove(bestPlayers.get(0));
        }

        bestPlayers.sort(Comparator.comparing(Player::getExpectedPoints).reversed().thenComparing(Player::getPrice));

        TeamBuilderService teamBuilderService = new TeamBuilderService(wildcardTeam, bestPlayers, true, Constants.REDUCED_TEAM);

        wildcardTeam = teamBuilderService.buildTeam();
        wildcardTeam.sort(Comparator.comparing(Player::getPosition));


        return playerImageService.getPlayerImages(wildcardTeam);

    }

    public List<Player> getStartTeam(){
        List<Player> bestPlayers = getAlgorithmSortedPlayers();
        List<Player> wildcardTeam = new ArrayList<>();

        for(int i=0;i< Constants.SQUAD_SIZE;i++){
            wildcardTeam.add(bestPlayers.get(0));
            bestPlayers.remove(bestPlayers.get(0));
        }

        bestPlayers.sort(Comparator.comparing(Player::getSelectedBy).reversed().thenComparing(Player::getPrice));

        TeamBuilderService teamBuilderService = new TeamBuilderService(wildcardTeam, bestPlayers, true, Constants.FULL_SQUAD);

        wildcardTeam = teamBuilderService.buildTeam();
        wildcardTeam.sort(Comparator.comparing(Player::getPerformanceWeight));


        return playerImageService.getPlayerImages(wildcardTeam);

    }
}
