package com.filmland.subscription.repository;

import com.filmland.subscription.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findOneByEmail(String email);
}
