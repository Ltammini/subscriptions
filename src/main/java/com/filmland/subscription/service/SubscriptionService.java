package com.filmland.subscription.service;

import com.filmland.subscription.dto.SharedSubscriptionDto;
import com.filmland.subscription.dto.SubscribeDto;
import com.filmland.subscription.exception.NotFoundException;
import com.filmland.subscription.mapper.SubscriptionMapper;
import com.filmland.subscription.model.Customer;
import com.filmland.subscription.model.Subscription;
import com.filmland.subscription.repository.SubscriptionRepository;
import com.filmland.subscription.validator.SubscriptionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubscriptionService {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private SubscriptionMapper subscriptionMapper;

    @Autowired
    private SubscriptionValidator subscriptionValidator;

    /**
     * checks is customer  is exist and subscription is not subscribed
     * Add column to subscription table for the category subscription requested
     * adds subscription
     * @param subscribeDto
     */
    public void addSubscription(SubscribeDto subscribeDto) {
        Optional<Customer> customerOptional = customerService.findCustomerByEmail(subscribeDto.getEmail());
        if (!customerOptional.isPresent()) {
            throw new NotFoundException("Customer not found with username: " + subscribeDto.getEmail());
        }
        Long customerId = customerOptional.get().getCustomerId();
        subscriptionValidator.validateSubscription(customerId, subscribeDto.getName());
        Subscription subscription = subscriptionMapper.mapSubscription(customerId, subscribeDto);
        subscriptionRepository.save(subscription);

    }

    /**
     * Validates both owner and partner are exists
     * shares the subscrition category to another customer
     * splits content and price
     * @param subscriptionDto
     */
    public void shareSubscription(SharedSubscriptionDto subscriptionDto) {
        Optional<Customer> optionalSubscriber = customerService.findCustomerByEmail(subscriptionDto.getEmail());

        if (!optionalSubscriber.isPresent()) {
            throw new NotFoundException("Subscriber not found  " + subscriptionDto.getEmail());
        }

        Optional<Customer> optionalPartner = customerService.findCustomerByEmail(subscriptionDto.getCustomer());
        Long customerId = optionalSubscriber.get().getCustomerId();
        if (!optionalPartner.isPresent()) {
            throw new NotFoundException("Customer not found  " + subscriptionDto.getEmail());
        }
        Long partnerCustomerId = optionalPartner.get().getCustomerId();
        Subscription originalSubscription = getByCustomerIdAndName(subscriptionDto, customerId);

        subscriptionValidator.validateSubscription(partnerCustomerId, subscriptionDto.getSubscribedCategory());

        saveOriginalSubscription(originalSubscription);
        saveSharedSubscription(partnerCustomerId, originalSubscription);
    }


    /**
     * Adds subscription column for another customer identified by customer id
     * @param partnerCustomerId
     * @param originalSubscription
     */
    private void saveSharedSubscription(Long partnerCustomerId, Subscription originalSubscription) {
        Subscription subscription = subscriptionMapper.buildSharedSubscription(partnerCustomerId, originalSubscription);
        subscriptionRepository.save(subscription);
    }


    /**
     * updates owners subscribed category after sharing to another customer
     * @param originalSubscription
     */
    private void saveOriginalSubscription(Subscription originalSubscription) {
        originalSubscription.setPrice(originalSubscription.getPrice() / 2);
        originalSubscription.setRemainingContent(originalSubscription.getRemainingContent() / 2);
        subscriptionRepository.save(originalSubscription);
    }

    /**
     * Fetches subscriber category by customer id and category name
     * @param subscriptionDto
     * @param custId
     * @return
     */
    private Subscription getByCustomerIdAndName(SharedSubscriptionDto subscriptionDto, Long custId) {
        return subscriptionRepository.findByCustomerIdAndName(custId, subscriptionDto.getSubscribedCategory());
    }

}
