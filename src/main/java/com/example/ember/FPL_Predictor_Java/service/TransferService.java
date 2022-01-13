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
                    switch (player.getPosition()){
                        case 1:
                            player.setBenchFodder(player.getPrice() <= Constants.MIN_GOALKEEPER_PRICE);
                        case 2:
                            player.setBenchFodder(player.getPrice() <= Constants.MIN_DEFENDER_PRICE);
                        case 3:
                            player.setBenchFodder(player.getPrice() <= Constants.MIN_MIDFIELDER_PRICE);
                        case 4:
                            player.setBenchFodder(player.getPrice() <= Constants.MIN_STRIKER_PRICE);
                        break;
                    }
                    this.userTeam.add(player);
                }
            }
        }

        List<Player> transferOptions = new ArrayList<>();

        for(Player player:this.userTeam){
            if(player.getGwPoints()==0){
                player.setPerformanceWeight(player.getExpectedPoints() < 0 ? 0 : player.getExpectedPoints() * player.getSelectedBy());
            }
            else{
                player.setPerformanceWeight(player.getExpectedPoints() < 0 ? 0 : player.getExpectedPoints() * player.getGwPoints() * player.getSelectedBy());

            }
            if(player.getPerformanceWeight()==0 && player.getNews()==null){
                player.setPerformanceWeight(player.getSelectedBy()/10);
            }
            if(player.getPerformanceWeight()==0 && player.getNews()!=null){
                player.setPerformanceWeight(1/player.getSelectedBy());
            }
            if(!player.getBenchFodder()){
                transferOptions.add(player);

            }

        }


        transferOptions.sort(Comparator.comparing(Player::getPerformanceWeight));
        return transferOptions.get(0);

    }

    private Player getIncomingPlayer(int position, Float price, int team, int bank){
        List<Player> allPlayers = fplBootstrapRepository.getAllPlayers();
        Player incomingPlayer = null;

        allPlayers.removeIf(p -> p.getPosition() != position);

        for(Player player:allPlayers){
            Float weight = player.getExpectedPoints() * player.getGwPoints() * player.getSelectedBy();
            player.setPerformanceWeight(weight);
        }
        allPlayers.sort(Comparator.comparing(Player::getPerformanceWeight));

        for(Player player : allPlayers) {
            if(this.userTeam.stream().noneMatch(o -> o.getId() == player.getId()) && player.getPrice() <= price - bank){
                if(player.getTeam()==team) {
                    incomingPlayer=player;
                }
                else if(this.userTeam.stream().filter(o -> o.getTeam() == player.getTeam()).mapToInt(Player::getTeam).count()!=Constants.MAX_TEAM_MEMBERS)
                {
                    incomingPlayer=player;

                }
            }
        }

        return incomingPlayer;
    }
}
