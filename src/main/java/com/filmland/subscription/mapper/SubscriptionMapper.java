package com.filmland.subscription.mapper;

import com.filmland.subscription.dto.SubscribeDto;
import com.filmland.subscription.model.Category;
import com.filmland.subscription.model.Subscription;
import com.filmland.subscription.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SubscriptionMapper {

    @Autowired
    private CategoryService categoryService;

    public Subscription buildSharedSubscription(Long partnerCustomerId, Subscription originalSubscription) {
        return Subscription.builder()
                .customerId(partnerCustomerId)
                .name(originalSubscription.getName())
                .price(originalSubscription.getPrice() / 2)
                .startDate(originalSubscription.getStartDate())
                .owner(originalSubscription.getOwner())
                .remainingContent(originalSubscription.getRemainingContent() / 2)
                .build();
    }

    public Subscription mapSubscription(Long customerId, SubscribeDto subscribeDto) {
        Category category = categoryService.getCategoryByName(subscribeDto.getName());
        return Subscription.builder()
                .customerId(customerId)
                .name(category.getName())
                .price(category.getPrice())
                .startDate(LocalDateTime.now())
                .owner(subscribeDto.getEmail())
                .remainingContent(category.getAvailableContent())
                .build();
    }
}
