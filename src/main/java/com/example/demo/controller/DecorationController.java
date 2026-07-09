package com.example.demo.controller;

import com.example.demo.dto.BuyDecorationRequestDto;
import com.example.demo.dto.DecorationDto;
import com.example.demo.service.DecorationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/decorations")
@CrossOrigin(origins = "*")
public class DecorationController {

    private final DecorationService decorationService;

    public DecorationController(DecorationService decorationService) {
        this.decorationService = decorationService;
    }

    @GetMapping
    public ResponseEntity<List<DecorationDto>> getAllDecorations() {
        return ResponseEntity.ok(decorationService.getAllDecorations());
    }

    @PostMapping("/buy")
    public ResponseEntity<DecorationDto> buyDecoration(@RequestBody BuyDecorationRequestDto request) {
        return ResponseEntity.ok(decorationService.buyDecoration(request));
    }
}
