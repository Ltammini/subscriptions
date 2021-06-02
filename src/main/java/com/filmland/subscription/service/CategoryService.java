package com.filmland.subscription.service;

import com.filmland.subscription.dto.AllCategoriesDto;
import com.filmland.subscription.exception.NotFoundException;
import com.filmland.subscription.mapper.CategoriesMapper;
import com.filmland.subscription.model.Category;
import com.filmland.subscription.model.Customer;
import com.filmland.subscription.model.Subscription;
import com.filmland.subscription.repository.CategoryRepository;
import com.filmland.subscription.repository.CustomerRepository;
import com.filmland.subscription.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private CategoriesMapper categoriesMapper;


    public AllCategoriesDto getCategories(String username) {
        Optional<Customer> customer = Optional.ofNullable(customerRepository.findOneByEmail(username));
        if (customer.isPresent()) {
            List<Subscription> subscribed = subscriptionRepository.findAllByCustomerId(customer.get().getCustomerId());
            List<Category> available = getAvailableCategories(subscribed);
            return categoriesMapper.mapSubscribedCategories(available, subscribed);
        }
        throw new NotFoundException("Customer not found with username: " + username);
    }

    public Category getCategoryByName(String name){
         return categoryRepository.findByName(name);
    }

    private List<Category> getAvailableCategories(List<Subscription> subscribed) {
        List<String> subscribedCategories = subscribed.stream()
                .map(Subscription::getName)
                .collect(Collectors.toList());
        return categoryRepository.findByNameNotIn(subscribedCategories);
    }
}


