package com.example.ember.FPL_Predictor_Java.web;

import com.example.ember.FPL_Predictor_Java.entity.Player;
import com.example.ember.FPL_Predictor_Java.service.FPLBootstrapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/wildcard")
public class WildcardTeamWebController {

    @Autowired
    private final FPLBootstrapService fplBootstrapService;

    HttpHeaders headers = new HttpHeaders();
    HttpEntity<String> request = new HttpEntity<String>(headers);
    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public WildcardTeamWebController(FPLBootstrapService fplBootstrapService) {
        this.fplBootstrapService = fplBootstrapService;
    }

    @GetMapping
    public String getWildcardTeam(Model model) throws IOException {
        List<Player> players = this.fplBootstrapService.getWildcardTeam();
        model.addAttribute("wildcard", players);
        return "wildcard";
    }
}