package com.example.demo.dto;

import java.time.LocalDateTime;

public class GameOrderDto {

    private Long id;
    private Long restaurantId;
    private Long recipeId;
    private String recipeName;
    private int coinsEarned;
    private LocalDateTime cookedAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getRestaurantId() { return restaurantId; }
    public void setRestaurantId(Long restaurantId) { this.restaurantId = restaurantId; }

    public Long getRecipeId() { return recipeId; }
    public void setRecipeId(Long recipeId) { this.recipeId = recipeId; }

    public String getRecipeName() { return recipeName; }
    public void setRecipeName(String recipeName) { this.recipeName = recipeName; }

    public int getCoinsEarned() { return coinsEarned; }
    public void setCoinsEarned(int coinsEarned) { this.coinsEarned = coinsEarned; }

    public LocalDateTime getCookedAt() { return cookedAt; }
    public void setCookedAt(LocalDateTime cookedAt) { this.cookedAt = cookedAt; }
}
