package com.filmland.subscription.validator;

import com.filmland.subscription.exception.BadRequestException;
import com.filmland.subscription.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionValidator {
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public void validateSubscription(Long customerId, String name) {
        if (subscriptionRepository.existsByCustomerIdAndName(customerId, name)) {
            throw new BadRequestException("You have already subscribed to category: " + name);
        }
    }
}
