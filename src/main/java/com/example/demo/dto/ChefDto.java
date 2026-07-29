package com.example.demo.dto;

public class ChefDto {

    private Long id;
    private String name;
    private boolean hired;
    private int level;
    private int hireCost;
    private int upgradeCost;

    public ChefDto() {
    }

    public ChefDto(
            Long id,
            String name,
            boolean hired,
            int level,
            int hireCost,
            int upgradeCost
    ) {
        this.id = id;
        this.name = name;
        this.hired = hired;
        this.level = level;
        this.hireCost = hireCost;
        this.upgradeCost = upgradeCost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHired() {
        return hired;
    }

    public void setHired(boolean hired) {
        this.hired = hired;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getHireCost() {
        return hireCost;
    }

    public void setHireCost(int hireCost) {
        this.hireCost = hireCost;
    }

    public int getUpgradeCost() {
        return upgradeCost;
    }

    public void setUpgradeCost(int upgradeCost) {
        this.upgradeCost = upgradeCost;
    }
}