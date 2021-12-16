package com.example.ember.FPL_Predictor_Java.web;

import com.example.ember.FPL_Predictor_Java.entity.Player;
import com.example.ember.FPL_Predictor_Java.repo.FPLBootstrapRepository;
import com.example.ember.FPL_Predictor_Java.service.FPLBootstrapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/kings_of_gameweek")
public class KingsOfGameweekWebController {

    @Autowired
    private final FPLBootstrapService fplBootstrapService;

    @Autowired
    public KingsOfGameweekWebController(FPLBootstrapService fplBootstrapService) {
        this.fplBootstrapService = fplBootstrapService;
    }

    @GetMapping
    public String getPlayers(Model model) throws IOException {
        List<Player> players = this.fplBootstrapService.getKingsOfGameweek();

        model.addAttribute("kings", players);
        return "kings";
    }
}