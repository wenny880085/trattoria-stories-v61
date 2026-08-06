package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.CookRequestDto;
import com.example.demo.dto.GameOrderDto;
import com.example.demo.entity.Chef;
import com.example.demo.entity.GameOrder;
import com.example.demo.entity.Recipe;
import com.example.demo.entity.Restaurant;
import com.example.demo.exception.GameException;
import com.example.demo.repository.ChefRepository;
import com.example.demo.repository.DecorationRepository;
import com.example.demo.repository.GameOrderRepository;
import com.example.demo.repository.RecipeRepository;
import com.example.demo.repository.RestaurantRepository;

@Service
public class OrderService {

    private final GameOrderRepository orderRepository;
    private final RecipeRepository recipeRepository;
    private final RestaurantRepository restaurantRepository;
    private final ChefRepository chefRepository;
    private final DecorationRepository decorationRepository;

    public OrderService(GameOrderRepository orderRepository,
                        RecipeRepository recipeRepository,
                        RestaurantRepository restaurantRepository,
                        ChefRepository chefRepository,
                        DecorationRepository decorationRepository) {
        this.orderRepository = orderRepository;
        this.recipeRepository = recipeRepository;
        this.restaurantRepository = restaurantRepository;
        this.chefRepository = chefRepository;
        this.decorationRepository = decorationRepository;
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

        // 取得廚師等級；尚未聘僱則視為 Lv.0
        Chef hiredChef = chefRepository.findAll()
            .stream()
            .filter(chef -> chef != null && chef.isHired())
            .findFirst()
            .orElse(null);
        
        int chefLevel = hiredChef == null ? 0 : hiredChef.getLevel();

        // 暫時以已購買的裝潢數量作為裝潢等級
        int decorationLevel = (int) decorationRepository.findAll()
            .stream()
            .filter(decoration -> decoration != null && decoration.isPurchased())
            .count();

        decorationLevel = Math.min(decorationLevel, 5);

        // 服務員後端尚未完成，暫時以 Lv.0 計算
        int waiterLevel = 0;
        
        int atmosphereGain = rollScoreByLevel(decorationLevel);
        int foodGain = rollScoreByLevel(chefLevel);
        int serviceGain = rollScoreByLevel(waiterLevel);
        
        restaurant.setAtmosphereScore(
            restaurant.getAtmosphereScore() + atmosphereGain);

        restaurant.setFoodScore(
            restaurant.getFoodScore() + foodGain);

        restaurant.setServiceScore(
            restaurant.getServiceScore() + serviceGain);

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
    
    private int rollScoreByLevel(int level) {
    int safeLevel = Math.max(0, Math.min(5, level));

        double[] chanceOfTen = {
            0.05,
            0.12,
            0.25,
            0.42,
            0.60,
            0.80
        };

        if (ThreadLocalRandom.current().nextDouble()
            < chanceOfTen[safeLevel]) {return 10;
        }

        int min = Math.max(1, safeLevel);
        int max = Math.min(9, 4 + safeLevel);

        return ThreadLocalRandom.current()
            .nextInt(min, max + 1);
    }


}
