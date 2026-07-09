package com.example.demo.init;

import com.example.demo.entity.Decoration;
import com.example.demo.entity.Recipe;
import com.example.demo.entity.Restaurant;
import com.example.demo.repository.DecorationRepository;
import com.example.demo.repository.RecipeRepository;
import com.example.demo.repository.RestaurantRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RestaurantRepository restaurantRepository;
    private final RecipeRepository recipeRepository;
    private final DecorationRepository decorationRepository;

    public DataInitializer(RestaurantRepository restaurantRepository,
                           RecipeRepository recipeRepository,
                           DecorationRepository decorationRepository) {
        this.restaurantRepository = restaurantRepository;
        this.recipeRepository = recipeRepository;
        this.decorationRepository = decorationRepository;
    }

    @Override
    public void run(String... args) {
        initRestaurant();
        initRecipes();
        initDecorations();
    }

    private void initRestaurant() {
        if (restaurantRepository.count() == 0) {
            Restaurant restaurant = new Restaurant();
            restaurant.setName("Trattoria Stories");
            restaurant.setCoins(100L);
            restaurant.setLevel(1);
            restaurant.setExperience(0);
            restaurantRepository.save(restaurant);
        }
    }

    private void initRecipes() {
        if (recipeRepository.count() == 0) {
            List<Recipe> recipes = Arrays.asList(
                buildRecipe("番茄義大利麵", "經典的番茄醬義大利麵，簡單美味", 10, 30, 120, true),
                buildRecipe("奶油培根麵",   "濃郁奶油培根風味，深受顧客喜愛",  15, 45, 150, true),
                buildRecipe("瑪格麗特披薩", "簡單道地的義式披薩",              20, 60, 180, true),
                buildRecipe("提拉米蘇",     "義式經典甜點，風味絕佳",          25, 80, 200, false),
                buildRecipe("海鮮燉飯",     "新鮮海鮮入菜的義式燉飯",          30, 100, 240, false)
            );
            recipeRepository.saveAll(recipes);
        }
    }

    private Recipe buildRecipe(String name, String description,
                                int cost, int price, int time, boolean unlocked) {
        Recipe recipe = new Recipe();
        recipe.setName(name);
        recipe.setDescription(description);
        recipe.setCookingCost(cost);
        recipe.setSellingPrice(price);
        recipe.setCookingTimeSeconds(time);
        recipe.setUnlocked(unlocked);
        return recipe;
    }

    private void initDecorations() {
        if (decorationRepository.count() == 0) {
            List<Decoration> decorations = Arrays.asList(
                buildDecoration("木質餐桌",   "溫暖的木質餐桌，提升用餐氛圍",    50,  "傢具"),
                buildDecoration("復古掛燈",   "義式風格掛燈，柔和的燈光",        80,  "燈具"),
                buildDecoration("花卉壁紙",   "清新花卉裝飾壁紙，煥然一新",      40,  "壁面"),
                buildDecoration("大理石吧台", "高雅大理石材質吧台，質感十足",    120, "傢具"),
                buildDecoration("霓虹招牌",   "引人注目的霓虹招牌，吸引更多顧客", 100, "招牌")
            );
            decorationRepository.saveAll(decorations);
        }
    }

    private Decoration buildDecoration(String name, String description,
                                        int price, String category) {
        Decoration decoration = new Decoration();
        decoration.setName(name);
        decoration.setDescription(description);
        decoration.setPrice(price);
        decoration.setCategory(category);
        decoration.setPurchased(false);
        decoration.setPurchasedAt(null);
        return decoration;
    }
}
