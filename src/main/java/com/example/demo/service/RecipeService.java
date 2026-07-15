package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.dto.RecipeDto;
import com.example.demo.entity.Recipe;
import com.example.demo.repository.RecipeRepository;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<RecipeDto> getAllRecipes() {
        return recipeRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private RecipeDto toDto(Recipe r) {
     RecipeDto dto = new RecipeDto();

     dto.setId(r.getId());
     dto.setIcon(r.getIcon());
     dto.setCategory(r.getCategory());
     dto.setName(r.getName());
     dto.setDescription(r.getDescription());
     dto.setCookingCost(r.getCookingCost());
     dto.setSellingPrice(r.getSellingPrice());
     dto.setCookingTimeSeconds(r.getCookingTimeSeconds());
     dto.setUnlocked(r.isUnlocked());
     dto.setNeedChefLevel(r.getNeedChefLevel());

     return dto;
     }
}
