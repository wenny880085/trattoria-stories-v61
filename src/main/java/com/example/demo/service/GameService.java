package com.example.demo.service;

import com.example.demo.entity.Decoration;
import com.example.demo.entity.Restaurant;
import com.example.demo.repository.DecorationRepository;
import com.example.demo.repository.GameOrderRepository;
import com.example.demo.repository.RestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GameService {

    private final RestaurantRepository restaurantRepository;
    private final DecorationRepository decorationRepository;
    private final GameOrderRepository orderRepository;

    public GameService(RestaurantRepository restaurantRepository,
                       DecorationRepository decorationRepository,
                       GameOrderRepository orderRepository) {
        this.restaurantRepository = restaurantRepository;
        this.decorationRepository = decorationRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public void resetGame() {
        // 重置餐廳狀態
        Restaurant restaurant = restaurantRepository.findById(1L).orElse(null);
        if (restaurant != null) {
            restaurant.setCoins(100L);
            restaurant.setLevel(1);
            restaurant.setExperience(0);
            restaurantRepository.save(restaurant);
        }

        // 重置所有裝潢為未購買
        List<Decoration> decorations = decorationRepository.findAll();
        for (Decoration d : decorations) {
            d.setPurchased(false);
            d.setPurchasedAt(null);
        }
        decorationRepository.saveAll(decorations);

        // 清除所有訂單記錄
        orderRepository.deleteAll();
    }
}
