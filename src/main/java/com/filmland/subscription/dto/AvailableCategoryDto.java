package com.filmland.subscription.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class AvailableCategoryDto extends CategoryDto {
    private int availableContent;
}
