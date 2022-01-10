package com.example.ember.FPL_Predictor_Java.service;

import com.example.ember.FPL_Predictor_Java.entity.Player;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
public class PlayerImageService {

    public List<Player> getPlayerImages(List<Player> team){
        for(Player player:team){
            String template = "https://resources.premierleague.com/premierleague/photos/players/110x140/p{0}.png";
            String result = MessageFormat.format(template, String.valueOf(player.getCode()));
            player.setImageURL(result);
        }

        return team;
    }
}
