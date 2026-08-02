package com.vipha.ecommerce.features.media;

import lombok.Builder;

@Builder
public record MediaResponse(
        Integer id,
        String name,
        String extension,
        Float size,
        String mediaType,
        Boolean isDraft,
        String measurement,
        String uri
) {
}
