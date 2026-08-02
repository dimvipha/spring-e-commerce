package com.vipha.ecommerce.features.category.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
public record CategoryResponse(
        Integer id,
        String cateName,
        String icon,
        String description,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        CategoryResponse parentCategory
) {
}