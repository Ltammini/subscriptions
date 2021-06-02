package com.filmland.subscription.mapper;

import com.filmland.subscription.dto.AllCategoriesDto;
import com.filmland.subscription.dto.AvailableCategoryDto;
import com.filmland.subscription.dto.SubscribedCategoryDto;
import com.filmland.subscription.model.Category;
import com.filmland.subscription.model.Subscription;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoriesMapper {

    public AllCategoriesDto mapSubscribedCategories(List<Category> available, List<Subscription> subscribed) {
        return AllCategoriesDto.builder()
                .availableCategories(available.stream().map(this::mapAvailableCategories).collect(Collectors.toList()))
                .subscribedCategories(subscribed.stream().map(this::mapSubscribedCategories).collect(Collectors.toList()))
                .build();
    }

    private SubscribedCategoryDto mapSubscribedCategories(Subscription subscription) {
        SubscribedCategoryDto subscribedCategoryDto = SubscribedCategoryDto.builder().build();
        subscribedCategoryDto.setName(subscription.getName());
        subscribedCategoryDto.setPrice(subscription.getPrice());
        subscribedCategoryDto.setRemainingContent(subscription.getRemainingContent());
        subscribedCategoryDto.setStartDate(subscription.getStartDate());
        return subscribedCategoryDto;

    }

    private AvailableCategoryDto mapAvailableCategories(Category category) {
        AvailableCategoryDto categoryDto = AvailableCategoryDto.builder().build();
        categoryDto.setName(category.getName());
        categoryDto.setPrice(category.getPrice());
        categoryDto.setAvailableContent(category.getAvailableContent());
        return categoryDto;
    }

}
