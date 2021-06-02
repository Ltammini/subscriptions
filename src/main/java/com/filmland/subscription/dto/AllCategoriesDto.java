package com.filmland.subscription.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AllCategoriesDto {
    private List<CategoryDto> availableCategories;
    private List<SubscribedCategoryDto> subscribedCategories;
}
