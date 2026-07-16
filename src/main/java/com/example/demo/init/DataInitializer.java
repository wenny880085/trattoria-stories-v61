package com.example.demo.init;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Decoration;
import com.example.demo.entity.Recipe;
import com.example.demo.entity.Restaurant;
import com.example.demo.repository.DecorationRepository;
import com.example.demo.repository.RecipeRepository;
import com.example.demo.repository.RestaurantRepository;

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
            buildRecipe(
                "🫒",
                "義大利麵",
                "清炒義大利麵",
                "開局基本料理。",
                25,
                90,
                120,
                true,
                0
            ),
            buildRecipe(
                "🌿",
                "義大利麵",
                "青醬義大利麵",
                "廚師 Lv2 解鎖。",
                45,
                130,
                150,
                false,
                2
            ),
            buildRecipe(
                "🍅",
                "義大利麵",
                "紅醬義大利麵",
                "廚師 Lv3 解鎖。",
                65,
                170,
                180,
                false,
                3
            ),
            buildRecipe(
                "🥛",
                "義大利麵",
                "白醬義大利麵",
                "廚師 Lv4 解鎖。",
                90,
                220,
                200,
                false,
                4
            ),
            buildRecipe(
                "🍮",
                "甜點",
                "提拉米蘇",
                "開局可販售甜點。",
                35,
                110,
                120,
                true,
                0
            ),
            buildRecipe(
                "🍷",
                "紅酒飲料",
                "紅酒飲料組",
                "增加客單價。",
                50,
                140,
                60,
                true,
                0
            )
           );

        recipeRepository.saveAll(recipes);
        }
    }

    private Recipe buildRecipe(
        String icon,
        String category,
        String name,
        String description,
        int cost,
        int price,
        int time,
        boolean unlocked,
        int needChefLevel) {

         Recipe recipe = new Recipe();

          recipe.setIcon(icon);
          recipe.setCategory(category);
          recipe.setName(name);
          recipe.setDescription(description);
          recipe.setCookingCost(cost);
          recipe.setSellingPrice(price);
          recipe.setCookingTimeSeconds(time);
          recipe.setUnlocked(unlocked);
          recipe.setNeedChefLevel(needChefLevel);

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
