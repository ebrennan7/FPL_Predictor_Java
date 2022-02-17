package com.example.ember.FPL_Predictor_Java.web;

import com.example.ember.FPL_Predictor_Java.entity.Player;
import com.example.ember.FPL_Predictor_Java.service.TransferService;
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
@RequestMapping("/transfer")
public class TransferWebController {

    @Autowired
    private final TransferService transferService;

    @Autowired
    public TransferWebController(TransferService transferService) {
        this.transferService = transferService;
    }

    @GetMapping(path = "/{userId}")
    public String getTeamForUser(@PathVariable(required=true,name="userId") String userId, Model model) throws IOException {

        List<Player> transfer = this.transferService.getTransfer(userId);
        model.addAttribute("transfer", transfer);
        return "transfer";
    }
}