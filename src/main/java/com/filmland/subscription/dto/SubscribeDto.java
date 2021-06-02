package com.filmland.subscription.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubscribeDto {
    private String name;
    private String email;
}
