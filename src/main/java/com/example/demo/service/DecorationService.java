package com.example.demo.service;

import com.example.demo.dto.BuyDecorationRequestDto;
import com.example.demo.dto.DecorationDto;
import com.example.demo.entity.Decoration;
import com.example.demo.entity.Restaurant;
import com.example.demo.exception.GameException;
import com.example.demo.repository.DecorationRepository;
import com.example.demo.repository.RestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DecorationService {

    private final DecorationRepository decorationRepository;
    private final RestaurantRepository restaurantRepository;

    public DecorationService(DecorationRepository decorationRepository,
                             RestaurantRepository restaurantRepository) {
        this.decorationRepository = decorationRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public List<DecorationDto> getAllDecorations() {
        return decorationRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public DecorationDto buyDecoration(BuyDecorationRequestDto request) {
        if (request.getDecorationId() == null) {
            throw new GameException("請選擇要購買的裝潢");
        }

        Restaurant restaurant = restaurantRepository.findById(1L)
                .orElseThrow(() -> new GameException("餐廳尚未初始化"));

        Decoration decoration = decorationRepository.findById(request.getDecorationId())
                .orElseThrow(() -> new GameException("裝潢不存在"));

        if (decoration.isPurchased()) {
            throw new GameException("此裝潢已購買");
        }

        if (restaurant.getCoins() < decoration.getPrice()) {
            throw new GameException("金幣不足，無法購買裝潢（需要 " + decoration.getPrice() + " 金幣）");
        }

        restaurant.setCoins(restaurant.getCoins() - decoration.getPrice());
        restaurantRepository.save(restaurant);

        decoration.setPurchased(true);
        decoration.setPurchasedAt(LocalDateTime.now());
        decoration = decorationRepository.save(decoration);

        return toDto(decoration);
    }

    private DecorationDto toDto(Decoration d) {
        DecorationDto dto = new DecorationDto();
        dto.setId(d.getId());
        dto.setName(d.getName());
        dto.setDescription(d.getDescription());
        dto.setPrice(d.getPrice());
        dto.setCategory(d.getCategory());
        dto.setPurchased(d.isPurchased());
        dto.setPurchasedAt(d.getPurchasedAt());
        return dto;
    }
}
