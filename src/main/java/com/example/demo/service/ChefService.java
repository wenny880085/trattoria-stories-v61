package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.ChefDto;
import com.example.demo.entity.Chef;
import com.example.demo.entity.Restaurant;
import com.example.demo.repository.ChefRepository;
import com.example.demo.repository.RestaurantRepository;

@Service
public class ChefService {

    private final ChefRepository chefRepository;
    private final RestaurantRepository restaurantRepository;

    public ChefService(
            ChefRepository chefRepository,
            RestaurantRepository restaurantRepository
    ) {
        this.chefRepository = chefRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public ChefDto getChef() {
        Chef chef = findChef();
        return toDto(chef);
    }

    @Transactional
    public ChefDto hireChef() {
        Chef chef = findChef();

        if (chef.isHired()) {
            throw new RuntimeException("廚師已經聘僱");
        }

        Restaurant restaurant = restaurantRepository.findById(1L)
                .orElseThrow(() ->
                        new RuntimeException("找不到餐廳資料")
                );

        if (restaurant.getCoins() < chef.getHireCost()) {
            throw new RuntimeException("金幣不足，無法聘僱廚師");
        }

        restaurant.setCoins(
                restaurant.getCoins() - chef.getHireCost()
        );

        chef.setHired(true);
        chef.setLevel(1);

        restaurantRepository.save(restaurant);
        Chef savedChef = chefRepository.save(chef);

        return toDto(savedChef);
    }

    private Chef findChef() {
        return chefRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("找不到廚師資料")
                );
    }

    private ChefDto toDto(Chef chef) {
        return new ChefDto(
                chef.getId(),
                chef.getName(),
                chef.isHired(),
                chef.getLevel(),
                chef.getHireCost(),
                chef.getUpgradeCost()
        );
    }
}