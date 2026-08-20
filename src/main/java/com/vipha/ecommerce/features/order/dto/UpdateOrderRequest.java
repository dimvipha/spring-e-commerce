package com.vipha.ecommerce.features.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UpdateOrderRequest(

        @NotBlank
        String address,

        @Size(max = 500)
        String remark,

//        @NotNull
//        Boolean status,

        List<@Valid UpdateOrderLineRequest> orderLines
) {
}