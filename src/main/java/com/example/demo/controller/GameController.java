package com.example.demo.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.GameService;

@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins = "*")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/reset")
    public ResponseEntity<Map<String, String>> resetGame() {
        gameService.resetGame();
        return ResponseEntity.ok(Map.of("message", "遊戲已重置"));
    }

    @PostMapping("/critic-success")
    public ResponseEntity<Map<String, String>> criticSuccess() {
        gameService.criticSuccess();
        return ResponseEntity.ok(Map.of("message", "美食評家挑戰成功，米其林升星"));
    }

    @PostMapping("/critic-six-star-success")
    public ResponseEntity<Map<String, String>> sixStarCriticSuccess() {
        gameService.sixStarCriticSuccess();
        return ResponseEntity.ok(Map.of("message", "六星美食評家挑戰成功"));
    }
}
