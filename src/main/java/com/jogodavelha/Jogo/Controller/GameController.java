package com.jogodavelha.Jogo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jogodavelha.Jogo.Model.JogoDaVelha;

@Controller
public class GameController {
    private JogoDaVelha game;
    private boolean gameOver;

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @PostMapping("/comece")
    public String comece(Model model){
        game = new JogoDaVelha();
        model.addAttribute("tabuleiro", game.getTabuleiro());
        return "comece";
    }
    @GetMapping("/jogada")
    public String playGame(@RequestParam("linha") int linha, @RequestParam("col") int col, Model model) {
        
        game.jogar(linha, col);
        
        model.addAttribute("tabuleiro", game.getTabuleiro());
        model.addAttribute("ganhador", game.getGanhador());
        model.addAttribute("gameOver", game.isGameOver());
       
        return "comece";
    }
}
