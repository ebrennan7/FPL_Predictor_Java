package com.example.ember.FPL_Predictor_Java.service;

import com.example.ember.FPL_Predictor_Java.entity.Gameweek;
import com.example.ember.FPL_Predictor_Java.entity.Player;
import com.example.ember.FPL_Predictor_Java.repo.FPLBootstrapRepository;
import com.example.ember.FPL_Predictor_Java.repo.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
public class GameweekService {

    FPLBootstrapRepository fplBootstrapRepository;


    @Autowired
    public GameweekService(FPLBootstrapRepository fplBootstrapRepository){
        this.fplBootstrapRepository=fplBootstrapRepository;

    }
    public int getCurrentGameweek(){
        List<Gameweek> gameweeks = fplBootstrapRepository.getGameweeks();

        for(Gameweek gameweek:gameweeks){
            if(gameweek.getCurrent()){
                return gameweek.getId();
            }
        }

        return 0;
    }
}
