package com.filmland.subscription.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class SubscribedCategoryDto extends CategoryDto {
    private int remainingContent;
    private LocalDateTime startDate;
}
