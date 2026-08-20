package com.vipha.ecommerce.features.order.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record OrderResponse(

        UUID id,

        String address,

        String customerId,

        Float discount,

        Instant orderedAt,

        String remark,

        Boolean status,

        Boolean isDeleted,

        List<OrderLineResponse> orderLines,
        BigDecimal totalAmount
) {
}