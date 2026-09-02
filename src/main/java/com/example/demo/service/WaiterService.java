package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Restaurant;
import com.example.demo.entity.Waiter;
import com.example.demo.repository.RestaurantRepository;
import com.example.demo.repository.WaiterRepository;

@Service
public class WaiterService {

    private final WaiterRepository waiterRepository;
    private final RestaurantRepository restaurantRepository;

    public WaiterService(
            WaiterRepository waiterRepository,
            RestaurantRepository restaurantRepository
    ) {
        this.waiterRepository = waiterRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public Waiter getWaiter() {
        return findWaiter();
    }

    @Transactional
    public Waiter hireWaiter() {
        Waiter waiter = findWaiter();

        if (waiter.isHired()) {
            throw new RuntimeException("服務員已經聘僱");
        }

        Restaurant restaurant = findRestaurant();

        int hireCost = 200;

        if (restaurant.getCoins() < hireCost) {
            throw new RuntimeException("金幣不足，無法聘僱服務員");
        }

        restaurant.setCoins(
                restaurant.getCoins() - hireCost
        );

        waiter.setHired(true);
        waiter.setLevel(1);
        waiter.setUpgradeCost(300);

        restaurantRepository.save(restaurant);
        return waiterRepository.save(waiter);
    }

    @Transactional
    public Waiter upgradeWaiter() {
        Waiter waiter = findWaiter();

        if (!waiter.isHired()) {
            throw new RuntimeException("請先聘僱服務員");
        }

        if (waiter.getLevel() >= 5) {
            throw new RuntimeException("服務員已經升到最高等級");
        }

        Restaurant restaurant = findRestaurant();

        int upgradeCost = waiter.getUpgradeCost();

        if (restaurant.getCoins() < upgradeCost) {
            throw new RuntimeException("金幣不足，無法升級服務員");
        }

        restaurant.setCoins(
                restaurant.getCoins() - upgradeCost
        );

        waiter.setLevel(waiter.getLevel() + 1);

        waiter.setUpgradeCost(
                calculateNextUpgradeCost(waiter.getLevel())
        );

        restaurantRepository.save(restaurant);
        return waiterRepository.save(waiter);
    }

    private int calculateNextUpgradeCost(int waiterLevel) {
        switch (waiterLevel) {
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

    private Waiter findWaiter() {
        return waiterRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("找不到服務員資料")
                );
    }

    private Restaurant findRestaurant() {
        return restaurantRepository.findById(1L)
                .orElseThrow(() ->
                        new RuntimeException("找不到餐廳資料")
                );
    }
}