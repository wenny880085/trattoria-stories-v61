package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Waiter;
import com.example.demo.service.WaiterService;

@RestController
@RequestMapping("/api/waiter")
public class WaiterController {

    private final WaiterService waiterService;

    public WaiterController(WaiterService waiterService) {
        this.waiterService = waiterService;
    }

    @GetMapping
    public ResponseEntity<Waiter> getWaiter() {
        return ResponseEntity.ok(
                waiterService.getWaiter()
        );
    }

    @PostMapping("/hire")
    public ResponseEntity<Waiter> hireWaiter() {
        return ResponseEntity.ok(
                waiterService.hireWaiter()
        );
    }

    @PostMapping("/upgrade")
    public ResponseEntity<Waiter> upgradeWaiter() {
        return ResponseEntity.ok(
                waiterService.upgradeWaiter()
        );
    }
}
