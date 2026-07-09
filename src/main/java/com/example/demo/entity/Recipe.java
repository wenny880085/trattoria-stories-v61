package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private int cookingCost;

    @Column(nullable = false)
    private int sellingPrice;

    @Column(nullable = false)
    private int cookingTimeSeconds;

    @Column(nullable = false)
    private boolean unlocked;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getCookingCost() { return cookingCost; }
    public void setCookingCost(int cookingCost) { this.cookingCost = cookingCost; }

    public int getSellingPrice() { return sellingPrice; }
    public void setSellingPrice(int sellingPrice) { this.sellingPrice = sellingPrice; }

    public int getCookingTimeSeconds() { return cookingTimeSeconds; }
    public void setCookingTimeSeconds(int cookingTimeSeconds) { this.cookingTimeSeconds = cookingTimeSeconds; }

    public boolean isUnlocked() { return unlocked; }
    public void setUnlocked(boolean unlocked) { this.unlocked = unlocked; }
}
