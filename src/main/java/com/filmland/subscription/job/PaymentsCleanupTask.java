package com.filmland.subscription.job;

import com.filmland.subscription.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PaymentsCleanupTask {
    @Autowired
    private PaymentService paymentService;

    /**
     * Daily cron job to trigger payment request
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void runPaymentRequest(){
        paymentService.createPaymentRequests();
    }
}
