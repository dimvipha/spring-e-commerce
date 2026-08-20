package com.vipha.ecommerce.features.order.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record UpdateOrderLineRequest(
        @NotNull
        Integer productId,

        @NotNull
        @Positive
        Integer qty
) {
}