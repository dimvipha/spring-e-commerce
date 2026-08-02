package com.vipha.ecommerce.features.product.dto;

import jakarta.validation.constraints.*;

import java.util.List;

public record CreateProductRequest(
        @NotBlank(message = "name is require")
        String name,
        @NotNull(message = "qty is required")
        @Min(0)
        Integer qty,
        @NotNull(message = "unitPrice is required")
        @Min(0)
        Integer unitPrice,

        String thumbnail,
        String description ,

        @NotNull(message = "status availability is required ")
        Boolean isAvailable,

        @NotNull(message = "category id is required ")
        @Positive
        Integer categoryId,

        @NotEmpty(message = "tag id is required at least one")
        @NotNull(message = "Tag id is required")
        List<Integer> tagIds

) {
}
