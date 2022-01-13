package com.example.ember.FPL_Predictor_Java.web;

import com.example.ember.FPL_Predictor_Java.entity.Player;
import com.example.ember.FPL_Predictor_Java.service.FPLBootstrapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/start")
public class StartTeamWebController {

    @Autowired
    private final FPLBootstrapService fplBootstrapService;

    @Autowired
    public StartTeamWebController(FPLBootstrapService fplBootstrapService) {
        this.fplBootstrapService = fplBootstrapService;
    }

    @GetMapping
    public String getWildcardTeam(Model model) throws IOException {
        List<Player> players = this.fplBootstrapService.getStartTeam();
        model.addAttribute("wildcard", players);
        return "wildcard";
    }
}