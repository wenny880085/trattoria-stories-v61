package com.example.demo.service;

import com.example.demo.dto.CookRequestDto;
import com.example.demo.dto.GameOrderDto;
import com.example.demo.entity.GameOrder;
import com.example.demo.entity.Recipe;
import com.example.demo.entity.Restaurant;
import com.example.demo.exception.GameException;
import com.example.demo.repository.GameOrderRepository;
import com.example.demo.repository.RecipeRepository;
import com.example.demo.repository.RestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final GameOrderRepository orderRepository;
    private final RecipeRepository recipeRepository;
    private final RestaurantRepository restaurantRepository;

    public OrderService(GameOrderRepository orderRepository,
                        RecipeRepository recipeRepository,
                        RestaurantRepository restaurantRepository) {
        this.orderRepository = orderRepository;
        this.recipeRepository = recipeRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    public GameOrderDto cookDish(CookRequestDto request) {
        if (request.getRecipeId() == null) {
            throw new GameException("請選擇要製作的食譜");
        }

        Restaurant restaurant = restaurantRepository.findById(1L)
                .orElseThrow(() -> new GameException("餐廳尚未初始化"));

        Recipe recipe = recipeRepository.findById(request.getRecipeId())
                .orElseThrow(() -> new GameException("食譜不存在"));

        if (!recipe.isUnlocked()) {
            throw new GameException("此食譜尚未解鎖");
        }

        if (restaurant.getCoins() < recipe.getCookingCost()) {
            throw new GameException("金幣不足，無法製作料理（需要 " + recipe.getCookingCost() + " 金幣）");
        }

        int netCoins = recipe.getSellingPrice() - recipe.getCookingCost();
        restaurant.setCoins(restaurant.getCoins() + netCoins);
        restaurantRepository.save(restaurant);

        GameOrder order = new GameOrder();
        order.setRestaurantId(restaurant.getId());
        order.setRecipeId(recipe.getId());
        order.setRecipeName(recipe.getName());
        order.setCoinsEarned(netCoins);
        order.setCookedAt(LocalDateTime.now());
        order = orderRepository.save(order);

        return toDto(order);
    }

    public List<GameOrderDto> getAllOrders() {
        return orderRepository.findAllByOrderByCookedAtDesc()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private GameOrderDto toDto(GameOrder o) {
        GameOrderDto dto = new GameOrderDto();
        dto.setId(o.getId());
        dto.setRestaurantId(o.getRestaurantId());
        dto.setRecipeId(o.getRecipeId());
        dto.setRecipeName(o.getRecipeName());
        dto.setCoinsEarned(o.getCoinsEarned());
        dto.setCookedAt(o.getCookedAt());
        return dto;
    }
}
