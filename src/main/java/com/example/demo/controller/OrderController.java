package com.example.demo.controller;

import com.example.demo.dto.CookRequestDto;
import com.example.demo.dto.GameOrderDto;
import com.example.demo.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/cook")
    public ResponseEntity<GameOrderDto> cookDish(@RequestBody CookRequestDto request) {
        return ResponseEntity.ok(orderService.cookDish(request));
    }

    @GetMapping
    public ResponseEntity<List<GameOrderDto>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }
}
