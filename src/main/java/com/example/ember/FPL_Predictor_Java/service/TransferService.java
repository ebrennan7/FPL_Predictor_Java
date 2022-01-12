package com.example.ember.FPL_Predictor_Java.service;

import com.example.ember.FPL_Predictor_Java.entity.Pick;
import com.example.ember.FPL_Predictor_Java.entity.Player;
import com.example.ember.FPL_Predictor_Java.repo.FPLBootstrapRepository;
import com.example.ember.FPL_Predictor_Java.repo.TeamRepository;
import com.example.ember.FPL_Predictor_Java.utilities.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TransferService {

    TeamRepository teamRepository;

    FPLBootstrapRepository fplBootstrapRepository;

    PlayerImageService playerImageService;

    GameweekService gameweekService;

    private List<Player> userTeam;

    @Autowired
    public TransferService(TeamRepository teamRepository, FPLBootstrapRepository fplBootstrapRepository, PlayerImageService playerImageService, GameweekService gameweekService){
        this.teamRepository=teamRepository;
        this.fplBootstrapRepository=fplBootstrapRepository;
        this.playerImageService=playerImageService;
        this.gameweekService=gameweekService;
    }

    public List<Player> getTransfer(String userId){
        List<Player> transfer = new ArrayList<>();
        Player outgoingPlayer = getOutgoingPlayer(userId);
        int bank = teamRepository.getUserBank(userId, String.valueOf(gameweekService.getCurrentGameweek()));
        transfer.add(outgoingPlayer);
        transfer.add(getIncomingPlayer(outgoingPlayer.getPosition(), outgoingPlayer.getPrice(), outgoingPlayer.getTeam(), bank));


        return playerImageService.getPlayerImages(transfer);
    }

    public Player getOutgoingPlayer(String userId){
        this.userTeam = new ArrayList<>();
        List<Pick> picks = teamRepository.getUserTeam(userId, String.valueOf(gameweekService.getCurrentGameweek()));
        List<Player> allPlayers = fplBootstrapRepository.getAllPlayers();

        for(Pick pick:picks){
            for(Player player:allPlayers){
                if(pick.getElement()==player.getId()){
                    this.userTeam.add(player);
                }
            }
        }
        TreeMap<Float, Player> playerMap = new TreeMap<Float, Player>();

        for(Player player:this.userTeam){
            float weight;
            if(player.getGwPoints()==0){
                weight = player.getExpectedPoints() * player.getSelectedBy();
            }
            else{
                weight = player.getExpectedPoints() * player.getGwPoints() * player.getSelectedBy();

            }
            if(weight==0 && player.getNews()==null){
                weight += player.getSelectedBy()/10;
            }
            if(weight==0 && player.getNews()!=null){
                weight += 1/player.getSelectedBy();
            }
            playerMap.put(weight, player);

        }

        //Multiple 0's as key is causing problems
        return playerMap.get(playerMap.firstKey());

    }

    private Player getIncomingPlayer(int position, Float price, int team, int bank){
        List<Player> allPlayers = fplBootstrapRepository.getAllPlayers();
        TreeMap<Float, Player> playerMap = new TreeMap<Float, Player>();

        allPlayers.removeIf(p -> p.getPosition() != position);

        for(Player player:allPlayers){
            Float weight = player.getExpectedPoints() * player.getGwPoints() * player.getSelectedBy();
            playerMap.put(weight, player);
        }
        for(Float key : playerMap.descendingKeySet()) {
            Long c = this.userTeam.stream().filter(o -> o.getTeam() == playerMap.get(key).getTeam()).mapToInt(Player::getTeam).count();
            if(this.userTeam.stream().noneMatch(o -> o.getId() == playerMap.get(key).getId()) && playerMap.get(key).getPrice() <= price - bank){
                if(playerMap.get(key).getTeam()==team) {
                    return playerMap.get(key);
                }
                else if(this.userTeam.stream().filter(o -> o.getTeam() == playerMap.get(key).getTeam()).mapToInt(Player::getTeam).count()!=Constants.MAX_TEAM_MEMBERS)
                {
                    return playerMap.get(key);

                }
            }
        }


        return playerMap.get(playerMap.lastKey());
    }
}
