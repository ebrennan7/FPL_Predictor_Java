package com.example.ember.FPL_Predictor_Java.service;

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

    @Autowired
    public FPLBootstrapService(FPLBootstrapRepository fplBootstrapRepository){
        this.fplBootstrapRepository=fplBootstrapRepository;
    }

    public List<Player> getAllPlayers(){
        return getPlayerImages(fplBootstrapRepository.getAllPlayers());
    }


    private List<Player> getPointsSortedPlayers(){
        List<Player> allPlayers = fplBootstrapRepository.getAllPlayers();
        allPlayers.sort(Comparator.comparing(Player::getGwPoints).reversed());

        return allPlayers;
    }

    private List<Player> getExpectedPointsSortedPlayers(){
        List<Player> allPlayers = fplBootstrapRepository.getAllPlayers();
        allPlayers.sort(Comparator.comparing(Player::getExpectedPoints).reversed());

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
        return getPlayerImages(dreamTeam);
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

        return getPlayerImages(kingsOfGameweekTeam);
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


        return getPlayerImages(wildcardTeam);

    }

    private List<Player> getPlayerImages(List<Player> team){
        for(Player player:team){
            String template = "https://resources.premierleague.com/premierleague/photos/players/110x140/p{0}.png";
            String result = MessageFormat.format(template, String.valueOf(player.getCode()));
            player.setImageURL(result);
        }

        return team;


    }

}
