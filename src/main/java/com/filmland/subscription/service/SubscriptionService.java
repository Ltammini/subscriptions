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

    public void shareSubscription(SharedSubscriptionDto subscriptionDto) {
        Optional<Customer> optionalSubscriber = customerService.findCustomerByEmail(subscriptionDto.getEmail());

        if (!optionalSubscriber.isPresent()) {
            throw new NotFoundException("subscriber not found  " + subscriptionDto.getEmail());
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

    private void saveSharedSubscription(Long partnerCustomerId, Subscription originalSubscription) {
        Subscription subscription = subscriptionMapper.buildSharedSubscription(partnerCustomerId, originalSubscription);
        subscriptionRepository.save(subscription);
    }

    private void saveOriginalSubscription(Subscription originalSubscription) {
        originalSubscription.setPrice(originalSubscription.getPrice() / 2);
        originalSubscription.setRemainingContent(originalSubscription.getRemainingContent() / 2);
        subscriptionRepository.save(originalSubscription);
    }

    private Subscription getByCustomerIdAndName(SharedSubscriptionDto subscriptionDto, Long custId) {
        return subscriptionRepository.findByCustomerIdAndName(custId, subscriptionDto.getSubscribedCategory());
    }

}
