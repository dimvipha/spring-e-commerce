package com.vipha.ecommerce.features.order.dto;


import java.math.BigDecimal;

public record OrderLineResponse(

        Long id,

        Integer productId,

        Integer qty,

        BigDecimal unitPrice
) {
}