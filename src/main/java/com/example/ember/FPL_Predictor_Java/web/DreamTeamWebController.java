package com.example.ember.FPL_Predictor_Java.web;

import com.example.ember.FPL_Predictor_Java.entity.Player;
import com.example.ember.FPL_Predictor_Java.entity.Response;
import com.example.ember.FPL_Predictor_Java.service.FPLBootstrapService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;

@Controller
@RequestMapping("/dream_team")
public class DreamTeamWebController {

    @Autowired
    private final FPLBootstrapService fplBootstrapService;

    HttpHeaders headers = new HttpHeaders();
    HttpEntity<String> request = new HttpEntity<String>(headers);
    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public DreamTeamWebController( FPLBootstrapService fplBootstrapService) {
        this.fplBootstrapService = fplBootstrapService;
    }

    @GetMapping
    public String getPlayers(Model model) throws IOException {
        List<Player> players = this.fplBootstrapService.getAllPlayers();
        model.addAttribute("players", players);
        return "players";
    }


    public void getAllPlayers() throws IOException {

        ResponseEntity<Response> responseEntity = restTemplate.getForEntity("https://fantasy.premierleague.com/api/bootstrap-static/", Response.class);
//        createPlayers(responseEntity.toString());
    }

//    private List<Player> createPlayers(String jsonStringResponse) throws IOException {
//        List<Player> players = new ArrayList<>();
////        Gson gson = new Gson();
////        Response response = gson.fromJson(jsonStringResponse, Response.class);
//        PlayerFromJson.read(jsonStringResponse).forEach(player ->
//                players.add(fplBootstrapService.createPlayer(player.getCode(),
//                        player.getFirstName(),
//                        player.getSecondName(),
//                        player.getGwPoints(),
//                        player.getPosition(),
//                        player.getPrice(),
//                        player.getExpectedPoints(),
//                        player.getTeam())));
//        return players;
//    }
//    private static class PlayerFromJson {
//        //fields
//        private String code, firstName, secondName, gwPoints, position, price,
//                expectedPoints, team;
//        //reader
//        static List<PlayerFromJson> read(String json) throws IOException {
//            return new ObjectMapper().setVisibility(FIELD, ANY).
//                    readValue(new FileInputStream(json), new TypeReference<List<PlayerFromJson>>() {});
//        }
//        protected PlayerFromJson(){}
//
//        Long getCode() { return Long.parseLong(code); }
//
//        String getFirstName() { return firstName; }
//
//        String getSecondName() { return secondName; }
//
//        Long getGwPoints() { return Long.parseLong(gwPoints); }
//
//        Long getPosition() { return Long.parseLong(price); }
//
//        Long getPrice() { return Long.parseLong(price); }
//
//        String getExpectedPoints() { return expectedPoints; }
//
//        Long getTeam() { return Long.parseLong(team); }
//
////        Difficulty getDifficulty() { return Difficulty.valueOf(difficulty); }
////
////        Region getRegion() { return Region.findByLabel(region); }
//    }
}