package com.filmland.subscription.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AvailableCategoryDto extends CategoryDto {
    private int availableContent;
}
