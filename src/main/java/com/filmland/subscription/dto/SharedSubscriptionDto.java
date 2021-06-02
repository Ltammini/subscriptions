package com.filmland.subscription.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SharedSubscriptionDto {
    private String email;
    private String customer;
    private String subscribedCategory;
}
