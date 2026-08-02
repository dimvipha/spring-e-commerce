package com.vipha.ecommerce.features.category.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CreateCategoryRequest(

        @NotBlank(message = "name is required bro")
        @Size( max = 50)
        String name,
        @Size(max=500)
        String description,
        @Size(max = 255)
        String icon,

        @Positive
        Integer parentCategoryId
) {
}