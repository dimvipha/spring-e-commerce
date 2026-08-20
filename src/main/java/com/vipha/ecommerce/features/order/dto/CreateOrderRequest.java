package com.vipha.ecommerce.features.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

public record CreateOrderRequest(

        @NotBlank(message = "address is required")
        String address,

        @Size(max = 500)
        String remark,

        @NotEmpty(message = "order lines is required")
        List<@Valid CreateOrderLineRequest> orderLines
) {
}