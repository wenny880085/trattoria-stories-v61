package com.example.demo.dto;

public class RecipeDto {

    private Long id;
    private String icon;
    private String category;
    private String name;
    private String description;
    private int cookingCost;
    private int sellingPrice;
    private int cookingTimeSeconds;
    private boolean unlocked;
    private int needChefLevel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCookingCost() {
        return cookingCost;
    }

    public void setCookingCost(int cookingCost) {
        this.cookingCost = cookingCost;
    }

    public int getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(int sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public int getCookingTimeSeconds() {
        return cookingTimeSeconds;
    }

    public void setCookingTimeSeconds(int cookingTimeSeconds) {
        this.cookingTimeSeconds = cookingTimeSeconds;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    public int getNeedChefLevel() {
        return needChefLevel;
    }

    public void setNeedChefLevel(int needChefLevel) {
        this.needChefLevel = needChefLevel;
    }
}