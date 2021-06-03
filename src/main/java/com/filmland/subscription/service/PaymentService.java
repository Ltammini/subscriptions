package com.filmland.subscription.service;

import com.filmland.subscription.model.Subscription;
import com.filmland.subscription.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    /**
     * Creates payment request object by fetching subscriptions
     * and creating mapping between customer ID and and sum of prices from subscriptions
     */
    public void createPaymentRequests() {
        List<Subscription> subscriptions = subscriptionRepository.findAllByStartDateAfter(LocalDateTime.now().minusMonths(1));
        Map<Long, Double> payments = subscriptions.stream().collect(
                Collectors.groupingBy(Subscription::getCustomerId, Collectors.summingDouble(Subscription::getPrice)));
    }
}
