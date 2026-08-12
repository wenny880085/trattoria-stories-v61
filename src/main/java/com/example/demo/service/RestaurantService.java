package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.dto.RestaurantDto;
import com.example.demo.entity.Restaurant;
import com.example.demo.exception.GameException;
import com.example.demo.repository.RestaurantRepository;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public RestaurantDto getRestaurant() {
        Restaurant restaurant = restaurantRepository.findById(1L)
                .orElseThrow(() -> new GameException("餐廳尚未初始化，請呼叫 POST /api/game/reset"));
        return toDto(restaurant);
    }

    private RestaurantDto toDto(Restaurant r) {
        RestaurantDto dto = new RestaurantDto();
        dto.setId(r.getId());
        dto.setName(r.getName());
        
        dto.setCoins(r.getCoins());
        dto.setLevel(r.getLevel());
        dto.setExperience(r.getExperience());

        dto.setMichelinStars(r.getMichelinStars());
        dto.setCriticStageCleared(r.isCriticStageCleared());

        // ⭐ 新增三項評分
        dto.setAtmosphereScore(r.getAtmosphereScore());
        dto.setFoodScore(r.getFoodScore());
        dto.setServiceScore(r.getServiceScore());
        
        return dto;
    }
}
