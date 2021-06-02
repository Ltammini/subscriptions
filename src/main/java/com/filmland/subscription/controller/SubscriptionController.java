package com.filmland.subscription.controller;

import com.filmland.subscription.dto.SharedSubscriptionDto;
import com.filmland.subscription.dto.SubscribeDto;
import com.filmland.subscription.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subscribe")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<?> subscribeCategory(@RequestBody SubscribeDto subscribeDto){
        subscriptionService.addSubscription(subscribeDto);
        return ResponseEntity.ok("Your subscription to "+subscribeDto.getName()+" is successful");
    }
    @PostMapping("/share")
    public ResponseEntity<?> subscribeCategory(@RequestBody SharedSubscriptionDto sharedSubscriptionDto){
        subscriptionService.shareSubscription(sharedSubscriptionDto);
        return ResponseEntity.ok("Your subscription "+ sharedSubscriptionDto.getSubscribedCategory()
                +" is shared successfully to "+ sharedSubscriptionDto.getCustomer());
    }
}
