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
            restaurant.setMichelinStars(0);
            restaurant.setCriticStageCleared(false);


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

    @Transactional
    public void criticSuccess() {
        Restaurant restaurant = restaurantRepository.findById(1L)
            .orElseThrow(() -> new RuntimeException("找不到餐廳資料"));

        if (restaurant.getAtmosphereScore() < 100 ||
           restaurant.getFoodScore() < 100 ||
           restaurant.getServiceScore() < 100) {
               throw new RuntimeException("三項評分尚未達到升星門檻");
            }
        
        if (restaurant.getMichelinStars() >= 6) {
            throw new RuntimeException("已經達到最高 6 星");
        }

            restaurant.setAtmosphereScore(restaurant.getAtmosphereScore() - 100);

            restaurant.setFoodScore(restaurant.getFoodScore() - 100);

            restaurant.setServiceScore(restaurant.getServiceScore() - 100);

            restaurant.setMichelinStars(restaurant.getMichelinStars() + 1);

            restaurant.setCriticStageCleared(false);

            restaurantRepository.save(restaurant);
    }
    @Transactional
    public void sixStarCriticSuccess() {
        Restaurant restaurant = restaurantRepository.findById(1L)
            .orElseThrow(() -> new RuntimeException("找不到餐廳資料"));

        // 必須已經是 6 星
        if (restaurant.getMichelinStars() < 6) {
          throw new RuntimeException("目前尚未達到六星餐廳");
        }

        // 三項評分都必須達到 100
        if (restaurant.getAtmosphereScore() < 100 ||
            restaurant.getFoodScore() < 100 ||
            restaurant.getServiceScore() < 100) {
              throw new RuntimeException("三項評分尚未達到美食評家挑戰門檻");
            }

        // ⭐ 六星後不再增加星數，只扣除三項評分
        restaurant.setAtmosphereScore(restaurant.getAtmosphereScore() - 100);
        restaurant.setFoodScore(restaurant.getFoodScore() - 100);
        restaurant.setServiceScore(restaurant.getServiceScore() - 100);
        // Michelin 維持 6 星，不做 +1
        restaurant.setCriticStageCleared(false);
        restaurantRepository.save(restaurant);
    }
}
