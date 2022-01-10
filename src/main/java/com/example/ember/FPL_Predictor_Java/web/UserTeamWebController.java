package com.example.ember.FPL_Predictor_Java.web;

import com.example.ember.FPL_Predictor_Java.entity.Player;
import com.example.ember.FPL_Predictor_Java.service.FPLBootstrapService;
import com.example.ember.FPL_Predictor_Java.service.UserPicksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/team")
public class UserTeamWebController {

    @Autowired
    private final UserPicksService userPicksService;

    @Autowired
    public UserTeamWebController(UserPicksService userPicksService) {
        this.userPicksService = userPicksService;
    }

    @GetMapping(path = "/{userId}")
    public String getTeamForUser(@PathVariable(required=true,name="userId") String userId, Model model) throws IOException {

        List<Player> players = this.userPicksService.getTeamForUser(userId);
        model.addAttribute("team", players);
        return "team";
    }
}