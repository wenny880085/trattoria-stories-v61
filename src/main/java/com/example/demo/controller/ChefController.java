package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ChefDto;
import com.example.demo.service.ChefService;

@RestController
@RequestMapping("/api/chef")
public class ChefController {

    private final ChefService chefService;

    public ChefController(ChefService chefService) {
        this.chefService = chefService;
    }

    @GetMapping
    public ChefDto getChef() {
        return chefService.getChef();
    }

    @PostMapping("/hire")
    public ChefDto hireChef() {
        return chefService.hireChef();
    }

    @PostMapping("/upgrade")
    public ChefDto upgradeChef() {
        return chefService.upgradeChef();
    }
}