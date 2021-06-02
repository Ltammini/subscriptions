package com.filmland.subscription.dto;

import lombok.Data;

@Data
public abstract class CategoryDto {
    private String name;
    private Double price;
}
