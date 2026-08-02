package com.vipha.ecommerce.features.product.dto;

import com.vipha.ecommerce.features.category.dto.CategoryResponse;
import com.vipha.ecommerce.features.tag.TagResponse;

import java.math.BigDecimal;
import java.util.List;

public record ProductResponse(
        Integer id,
        String code,
        String slug,
        String name,
        String description ,
        Integer qty,
        BigDecimal unitPrice,
        Boolean isAvailable,
        String thumbnail,

        CategoryResponse category,
        List<TagResponse> tags

) {
}
