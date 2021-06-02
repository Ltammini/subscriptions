package com.filmland.subscription.repository;

import com.filmland.subscription.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findAllByCustomerId(Long customerId);

    boolean existsByCustomerIdAndName(Long customerId, String name);

    Subscription findByCustomerIdAndName(Long customerId, String name);

    List<Subscription> findAllByStartDateAfter(LocalDateTime paymentDate);
}
