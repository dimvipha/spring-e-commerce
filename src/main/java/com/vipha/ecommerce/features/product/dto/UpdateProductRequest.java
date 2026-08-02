package com.vipha.ecommerce.features.product.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;

import java.util.List;

@Builder
public record UpdateProductRequest(
        String name,
        @NotNull(message = "qty is required")
        @Min(0)
        Integer qty,
        @Min(0)
        Integer unitPrice,
        String description ,
        Boolean isAvailable,

        @Positive
        Integer categoryId,
        @NotNull(message = "Tag id cannot be null ")
        List<Integer> tagIds

) {
}
