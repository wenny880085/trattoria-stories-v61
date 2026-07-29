package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "chefs")
public class Chef {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // 是否已經聘請
    private boolean hired;

    // 0 = 尚未聘請，聘請後從 1 級開始
    private int level;

    // 第一次聘請需要的金幣
    private int hireCost;

    // 下一次升級需要的金幣
    private int upgradeCost;

    public Chef() {
    }

    public Chef(
            String name,
            boolean hired,
            int level,
            int hireCost,
            int upgradeCost
    ) {
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