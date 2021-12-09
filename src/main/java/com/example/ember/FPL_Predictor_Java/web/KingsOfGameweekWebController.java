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
@RequestMapping("/kings_of_gameweek")
public class KingsOfGameweekWebController {

    @Autowired
    private final FPLBootstrapService fplBootstrapService;

    @Autowired
    public KingsOfGameweekWebController( FPLBootstrapService fplBootstrapService) {
        this.fplBootstrapService = fplBootstrapService;
    }

    @GetMapping
    public String getPlayers(Model model) throws IOException {
        List<Player> players = this.fplBootstrapService.getKingsOfGameweek();
        model.addAttribute("players", players);
        return "players";
    }
}