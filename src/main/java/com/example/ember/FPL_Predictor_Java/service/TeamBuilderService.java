package com.example.ember.FPL_Predictor_Java.service;

import com.example.ember.FPL_Predictor_Java.entity.Player;
import com.example.ember.FPL_Predictor_Java.utilities.Constants;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class TeamBuilderService {

    List<Player> players;
    List<Player> wildcardTeam;
    Boolean teamProtection=false;

    @Autowired
    public TeamBuilderService(List<Player> wildcardTeam, List<Player> players) {
        this.players=players;
        this.wildcardTeam=wildcardTeam;
    }

    public TeamBuilderService(List<Player> wildcardTeam, List<Player> players, Boolean teamProtection){
        this.players=players;
        this.wildcardTeam=wildcardTeam;
        this.teamProtection=teamProtection;
    }

    public List<Player> buildTeam(){
        goalieSwap();
        defenderSwap();
        midfielderSwap();
        strikerSwap();
        if(teamProtection){
            teamOverloadProtection();
        }

        return this.wildcardTeam;
    }

    private void goalieSwap(){
        int squadIndex = wildcardTeam.size() - 1;
        while(goalieCheck().equals(Constants.UNDERLOADED)){
            int positionOfPlayerToRemove = wildcardTeam.get(squadIndex).getPosition();
            prepareToSwap(squadIndex, positionOfPlayerToRemove, Constants.GOALKEEPER);
            squadIndex--;
        }

        while(goalieCheck().equals(Constants.OVERLOADED)){
            for(int i=wildcardTeam.size()-1;i>=0;i--){
                //Is the && clause needed?
                if(wildcardTeam.get(i).getPosition() == Constants.GOALKEEPER && goalieCheck().equals(Constants.OVERLOADED)){
                    wildcardTeam.remove(i);
                    addPlayer();
                }
            }
        }
    }

    private void defenderSwap(){
        int squadIndex = wildcardTeam.size() - 1;
        while(defenderCheck().equals(Constants.UNDERLOADED)){
            int positionOfPlayerToRemove = wildcardTeam.get(squadIndex).getPosition();
            prepareToSwap(squadIndex, positionOfPlayerToRemove, Constants.DEFENDER);
            squadIndex--;
        }

        while(defenderCheck().equals(Constants.OVERLOADED)){
            for(int i=wildcardTeam.size()-1;i>=0;i--){
                //Is the && clause needed?
                if(wildcardTeam.get(i).getPosition() == Constants.DEFENDER && defenderCheck().equals(Constants.OVERLOADED)){
                    wildcardTeam.remove(i);
                    addPlayer();
                }
            }
        }
    }

    private void midfielderSwap(){
        int squadIndex = wildcardTeam.size() - 1;
        while(midfielderCheck().equals(Constants.UNDERLOADED)){
            int positionOfPlayerToRemove = wildcardTeam.get(squadIndex).getPosition();
            prepareToSwap(squadIndex, positionOfPlayerToRemove, Constants.MIDFIELDER);
            squadIndex--;
        }

        while(midfielderCheck().equals(Constants.OVERLOADED)){
            for(int i=wildcardTeam.size()-1;i>=0;i--){
                //Is the && clause needed?
                if(wildcardTeam.get(i).getPosition() == Constants.MIDFIELDER && midfielderCheck().equals(Constants.OVERLOADED)){
                    wildcardTeam.remove(i);
                    addPlayer();
                }
            }
        }
    }

    private void strikerSwap(){
        int squadIndex = wildcardTeam.size() - 1;
        while(strikerCheck().equals(Constants.UNDERLOADED)){
            int positionOfPlayerToRemove = wildcardTeam.get(squadIndex).getPosition();
            prepareToSwap(squadIndex, positionOfPlayerToRemove, Constants.STRIKER);
            squadIndex--;
        }

        while(strikerCheck().equals(Constants.OVERLOADED)){
            for(int i=wildcardTeam.size()-1;i>=0;i--){
                //Is the && clause needed?
                if(wildcardTeam.get(i).getPosition() == Constants.STRIKER && strikerCheck().equals(Constants.OVERLOADED)){
                    wildcardTeam.remove(i);
                    addPlayer();
                }
            }
        }
    }

    private String goalieCheck(){
        if(this.wildcardTeam.stream().filter(o -> o.getPosition() == Constants.GOALKEEPER).mapToInt(Player::getPosition).count()==Constants.MIN_GOALKEEPER){
            return Constants.AT_MIN;
        }
        else if(this.wildcardTeam.stream().filter(o -> o.getPosition() == Constants.GOALKEEPER).mapToInt(Player::getPosition).count()<Constants.MIN_GOALKEEPER){
            return Constants.UNDERLOADED;
        }
        else if(wildcardTeam.stream().filter(o -> o.getPosition() == Constants.GOALKEEPER).mapToInt(Player::getPosition).count()>Constants.MAX_GOALKEEPER){
            return Constants.OVERLOADED;
        }
        else return Constants.OK;
    }
    private String defenderCheck(){
        if(this.wildcardTeam.stream().filter(o -> o.getPosition() == Constants.DEFENDER).mapToInt(Player::getPosition).count()>Constants.MAX_DEFENDER){
            System.out.println(this.wildcardTeam.stream().filter(o -> o.getPosition() == Constants.DEFENDER).mapToInt(Player::getPosition));
            return Constants.OVERLOADED;
        }
        else if(this.wildcardTeam.stream().filter(o -> o.getPosition() == Constants.DEFENDER).mapToInt(Player::getPosition).count()<Constants.MIN_DEFENDER){
            return Constants.UNDERLOADED;
        }

        else if(this.wildcardTeam.stream().filter(o -> o.getPosition() == Constants.DEFENDER).mapToInt(Player::getPosition).count()==Constants.MIN_DEFENDER){
            return Constants.AT_MIN;
        }
        else if(this.wildcardTeam.stream().filter(o -> o.getPosition() == Constants.DEFENDER).mapToInt(Player::getPosition).count()<Constants.MAX_DEFENDER){
            return Constants.HAS_ROOM;
        }
        else return Constants.OK;
    }
    private String midfielderCheck(){
        if(this.wildcardTeam.stream().filter(o -> o.getPosition() == Constants.MIDFIELDER).mapToInt(Player::getPosition).count()<Constants.MIN_MIDFIELDER){
            return Constants.UNDERLOADED;
        }
        else if(this.wildcardTeam.stream().filter(o -> o.getPosition() == Constants.MIDFIELDER).mapToInt(Player::getPosition).count()>Constants.MAX_MIDFIELDER){
            return Constants.OVERLOADED;
        }
        else if(this.wildcardTeam.stream().filter(o -> o.getPosition() == Constants.MIDFIELDER).mapToInt(Player::getPosition).count()==Constants.MIN_MIDFIELDER){
            return Constants.AT_MIN;
        }
        else if(this.wildcardTeam.stream().filter(o -> o.getPosition() == Constants.MIDFIELDER).mapToInt(Player::getPosition).count()<Constants.MAX_MIDFIELDER){
            return Constants.HAS_ROOM;
        }
        else return Constants.OK;
    }
    private String strikerCheck(){
        if(this.wildcardTeam.stream().filter(o -> o.getPosition() == Constants.STRIKER).mapToInt(Player::getPosition).count()<Constants.MIN_STRIKER){
            return Constants.UNDERLOADED;
        }
        else if(this.wildcardTeam.stream().filter(o -> o.getPosition() == Constants.STRIKER).mapToInt(Player::getPosition).count()>Constants.MAX_STRIKER){
            return Constants.OVERLOADED;
        }
        else if(this.wildcardTeam.stream().filter(o -> o.getPosition() == Constants.STRIKER).mapToInt(Player::getPosition).count()==Constants.MIN_STRIKER){
            return Constants.AT_MIN;
        }
        else if(this.wildcardTeam.stream().filter(o -> o.getPosition() == Constants.STRIKER).mapToInt(Player::getPosition).count()<Constants.MAX_STRIKER){
            return Constants.HAS_ROOM;
        }
        else return Constants.OK;
    }

    private void prepareToSwap(int squadIndex, int outgoingPosition, int incomingPosition){
        if(outgoingPosition == Constants.GOALKEEPER && !goalieCheck().equals(Constants.AT_MIN)){
            swapPlayer(squadIndex, incomingPosition);
        }
        if(outgoingPosition == Constants.DEFENDER && !defenderCheck().equals(Constants.AT_MIN)){
            swapPlayer(squadIndex, incomingPosition);
        }
        if(outgoingPosition == Constants.MIDFIELDER && !midfielderCheck().equals(Constants.AT_MIN)){
            swapPlayer(squadIndex, incomingPosition);
        }
        if(outgoingPosition == Constants.STRIKER && !strikerCheck().equals(Constants.AT_MIN)){
            swapPlayer(squadIndex, incomingPosition);
        }

    }

    private void swapPlayer(int squadIndex, int incomingPosition){
        this.wildcardTeam.remove(squadIndex);
        for(Player player:this.players){
            if(player.getPosition()==incomingPosition){
                this.wildcardTeam.add(player);
                players.remove(player);
                break;
            }
        }
    }

    private void addPlayer(){
        for(Player player:players){
            if(player.getPosition() == Constants.GOALKEEPER && goalieCheck().equals(Constants.UNDERLOADED)){
                wildcardTeam.add(player);
                players.remove(player);
                break;
            }
            else if(player.getPosition() == Constants.DEFENDER && (defenderCheck().equals(Constants.HAS_ROOM) || defenderCheck().equals(Constants.AT_MIN))){
                wildcardTeam.add(player);
                players.remove(player);
                break;
            }
            else if(player.getPosition() == Constants.MIDFIELDER && (midfielderCheck().equals(Constants.HAS_ROOM) || midfielderCheck().equals(Constants.AT_MIN))){
                wildcardTeam.add(player);
                players.remove(player);
                break;
            }
            else if(player.getPosition() == Constants.STRIKER && (strikerCheck().equals(Constants.HAS_ROOM) || strikerCheck().equals(Constants.AT_MIN))){
                wildcardTeam.add(player);
                players.remove(player);
                break;
            }
        }
    }

    private String deduceCheck(int position){
        switch (position){
            case 1:
                return goalieCheck();
            case 2:
                return defenderCheck();
            case 3:
                return midfielderCheck();
            case 4:
                return strikerCheck();
            default:
                return "Error";
        }
    }

    private void teamOverloadProtection(){
        for(int j=1;j<=20;j++){
            int finalJ = j;
            while(this.wildcardTeam.stream().filter(o -> o.getTeam() == finalJ).mapToInt(Player::getTeam).count()>Constants.MAX_TEAM_MEMBERS) {
                for(int i = wildcardTeam.size() - 1; i >= 0; i--) {
                    if(wildcardTeam.get(i).getTeam()==j && !deduceCheck(wildcardTeam.get(i).getPosition()).equals(Constants.AT_MIN)){
                        wildcardTeam.remove(i);
                        addPlayer();
                        break;
                    }
                    else if(wildcardTeam.get(i).getTeam()==j && deduceCheck(wildcardTeam.get(i).getPosition()).equals(Constants.AT_MIN)){
                        swapPlayer(i, wildcardTeam.get(i).getPosition());
                        break;
                    }
                }
            }
        }
    }


}
