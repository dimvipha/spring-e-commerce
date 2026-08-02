package com.vipha.ecommerce.features.tag;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record TagRequest(

        @NotBlank(message = "name is required bro")
        @Size(max = 50)
        String name
) {
}
