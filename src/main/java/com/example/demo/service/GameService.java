package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Chef;
import com.example.demo.entity.Decoration;
import com.example.demo.entity.Recipe;
import com.example.demo.entity.Restaurant;
import com.example.demo.repository.ChefRepository;
import com.example.demo.repository.DecorationRepository;
import com.example.demo.repository.GameOrderRepository;
import com.example.demo.repository.RecipeRepository;
import com.example.demo.repository.RestaurantRepository;

@Service
public class GameService {

    private final RestaurantRepository restaurantRepository;
    private final DecorationRepository decorationRepository;
    private final GameOrderRepository orderRepository;
    private final RecipeRepository recipeRepository;
    private final ChefRepository chefRepository;

    public GameService(RestaurantRepository restaurantRepository,
                       DecorationRepository decorationRepository,
                       GameOrderRepository orderRepository,
                       RecipeRepository recipeRepository,
                       ChefRepository chefRepository) {
        this.restaurantRepository = restaurantRepository;
        this.decorationRepository = decorationRepository;
        this.orderRepository = orderRepository;
        this.recipeRepository = recipeRepository;
        this.chefRepository = chefRepository;
    }

    @Transactional
    public void resetGame() {
        // 重置餐廳狀態
        Restaurant restaurant = restaurantRepository.findById(1L).orElse(null);
        if (restaurant != null) {
            restaurant.setCoins(100L);
            restaurant.setLevel(1);
            restaurant.setExperience(0);
            restaurant.setAtmosphereScore(0);
            restaurant.setFoodScore(0);
            restaurant.setServiceScore(0);


            restaurantRepository.save(restaurant);
        }

        // 重置所有裝潢為未購買
        List<Decoration> decorations = decorationRepository.findAll();
        for (Decoration d : decorations) {
            d.setPurchased(false);
            d.setPurchasedAt(null);
        }
        decorationRepository.saveAll(decorations);

        // 重置菜單解鎖狀態
        List<Recipe> recipes = recipeRepository.findAll();
        for (Recipe recipe : recipes) {
            boolean initiallyUnlocked = recipe.getNeedChefLevel() == 0;
            recipe.setUnlocked(initiallyUnlocked);
        }

        recipeRepository.saveAll(recipes);

        // 清除所有訂單記錄
        orderRepository.deleteAll();


        // 重置廚師狀態
        List<Chef> chefs = chefRepository.findAll();
       
        for (Chef chef : chefs) {
            chef.setHired(false);
            chef.setLevel(0);
            chef.setUpgradeCost(300);
        }
        chefRepository.saveAll(chefs);
    }
}
