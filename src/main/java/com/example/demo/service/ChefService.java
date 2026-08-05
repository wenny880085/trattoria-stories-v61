package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.ChefDto;
import com.example.demo.entity.Chef;
import com.example.demo.entity.Recipe;
import com.example.demo.entity.Restaurant;
import com.example.demo.repository.ChefRepository;
import com.example.demo.repository.RecipeRepository;
import com.example.demo.repository.RestaurantRepository;

@Service
public class ChefService {

    private final ChefRepository chefRepository;
    private final RestaurantRepository restaurantRepository;
    private final RecipeRepository recipeRepository;

    public ChefService(
            ChefRepository chefRepository,
            RestaurantRepository restaurantRepository,
            RecipeRepository recipeRepository
    ) {
        this.chefRepository = chefRepository;
        this.restaurantRepository = restaurantRepository;
        this.recipeRepository = recipeRepository;
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

        Restaurant restaurant = findRestaurant();

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

    @Transactional
    public ChefDto upgradeChef() {
        Chef chef = findChef();

        if (!chef.isHired()) {
            throw new RuntimeException("請先聘僱廚師");
        }

        if (chef.getLevel() >= 5) {
            throw new RuntimeException("廚師已經升到最高等級");
        }

        Restaurant restaurant = findRestaurant();

        int upgradeCost = chef.getUpgradeCost();

        if (restaurant.getCoins() < upgradeCost) {
            throw new RuntimeException("金幣不足，無法升級廚師");
        }

        restaurant.setCoins(
                restaurant.getCoins() - upgradeCost
        );

        chef.setLevel(chef.getLevel() + 1);

        unlockRecipeByChefLevel(chef.getLevel());

        chef.setUpgradeCost(
                calculateNextUpgradeCost(chef.getLevel())
        );

        restaurantRepository.save(restaurant);
        Chef savedChef = chefRepository.save(chef);

        return toDto(savedChef);
    }

    private void unlockRecipeByChefLevel(int chefLevel) {
        switch (chefLevel) {
            case 2:
                unlockRecipe("青醬義大利麵");
                break;

            case 3:
                unlockRecipe("紅醬義大利麵");
                break;

            case 4:
                unlockRecipe("白醬義大利麵");
                break;

            case 5:
                unlockRecipe("提拉米蘇");
                break;

            default:
                break;
        }
    }

    private void unlockRecipe(String recipeName) {
        Recipe recipe = recipeRepository.findByName(recipeName)
                .orElseThrow(() ->
                        new RuntimeException(
                                "找不到菜色：" + recipeName
                        )
                );

        recipe.setUnlocked(true);
        recipeRepository.save(recipe);
    }

    private int calculateNextUpgradeCost(int chefLevel) {
        switch (chefLevel) {
            case 2:
                return 500;

            case 3:
                return 700;

            case 4:
                return 1000;

            case 5:
                return 0;

            default:
                return 300;
        }
    }

    private Chef findChef() {
        return chefRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("找不到廚師資料")
                );
    }

    private Restaurant findRestaurant() {
        return restaurantRepository.findById(1L)
                .orElseThrow(() ->
                        new RuntimeException("找不到餐廳資料")
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