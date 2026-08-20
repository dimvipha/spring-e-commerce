package com.vipha.ecommerce.features.order.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateOrderLineRequest(

        @NotNull
        Integer productId,

        @NotNull
        @Positive(message = "qty must be greater than zero")
        Integer qty

//        @NotNull
//        @Positive(message = "unit price must be greater than zero")
//        BigDecimal unitPrice
) {
}