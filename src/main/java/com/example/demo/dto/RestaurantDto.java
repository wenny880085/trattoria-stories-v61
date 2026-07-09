package com.example.demo.dto;

public class RestaurantDto {

    private Long id;
    private String name;
    private long coins;
    private int level;
    private int experience;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public long getCoins() { return coins; }
    public void setCoins(long coins) { this.coins = coins; }

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }

    public int getExperience() { return experience; }
    public void setExperience(int experience) { this.experience = experience; }
}
