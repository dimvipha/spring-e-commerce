package com.vipha.ecommerce.exception;

import lombok.Builder;

import java.time.Instant;


@Builder
public record ApiErrorResponse <T>(
        Integer code,
        Boolean isSuccess,
        String message,
        Instant timestamp,
        T errorDetail
){

}
