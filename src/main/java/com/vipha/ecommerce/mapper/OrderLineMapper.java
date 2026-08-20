package com.vipha.ecommerce.mapper;

import com.vipha.ecommerce.features.order.OrderLine;
import com.vipha.ecommerce.features.order.dto.OrderLineResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderLineMapper {
    // Add this — MapStruct needs it to map List<OrderLine> -> List<OrderLineResponse>
    @Mapping(target = "productId", source = "product.id")
//     @Mapping(source = "product.name",target = "productName")
//     @Mapping(target = "lineTotal", expression = "java(orderLine.getUnitPrice().multiply(java.math.BigDecimal.valueOf(orderLine.getQty())))")
    OrderLineResponse toOrderLineResponse(OrderLine orderLine);
}
