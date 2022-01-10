package com.example.ember.FPL_Predictor_Java.service;

import com.example.ember.FPL_Predictor_Java.entity.Pick;
import com.example.ember.FPL_Predictor_Java.entity.Player;
import com.example.ember.FPL_Predictor_Java.repo.FPLBootstrapRepository;
import com.example.ember.FPL_Predictor_Java.repo.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserPicksService {

    TeamRepository teamRepository;

    FPLBootstrapRepository fplBootstrapRepository;

    PlayerImageService playerImageService;

    GameweekService gameweekService;

    @Autowired
    public UserPicksService(TeamRepository teamRepository, FPLBootstrapRepository fplBootstrapRepository, PlayerImageService playerImageService, GameweekService gameweekService){
        this.teamRepository=teamRepository;
        this.fplBootstrapRepository=fplBootstrapRepository;
        this.playerImageService=playerImageService;
        this.gameweekService=gameweekService;
    }

    public List<Player> getTeamForUser(String userId){
        List<Player> userTeam = new ArrayList<>();
        List<Pick> picks = teamRepository.getUserTeam(userId, String.valueOf(gameweekService.getCurrentGameweek()));
        List<Player> allPlayers = fplBootstrapRepository.getAllPlayers();

        for(Pick pick:picks){
            for(Player player:allPlayers){
                if(pick.getElement()==player.getId()){
                    userTeam.add(player);
                }
            }
        }

        return playerImageService.getPlayerImages(userTeam);
    }
}
