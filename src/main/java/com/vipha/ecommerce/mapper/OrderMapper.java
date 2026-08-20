package com.vipha.ecommerce.mapper;

import com.vipha.ecommerce.features.order.Order;
import com.vipha.ecommerce.features.order.OrderLine;
import com.vipha.ecommerce.features.order.dto.CreateOrderRequest;
import com.vipha.ecommerce.features.order.dto.OrderLineResponse;
import com.vipha.ecommerce.features.order.dto.OrderResponse;
import com.vipha.ecommerce.features.order.dto.UpdateOrderRequest;
import org.mapstruct.*;

import java.math.BigDecimal;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface OrderMapper {

     @Mapping(target = "totalAmount", expression = "java(calculateTotal(order))")
     OrderResponse toOrderResponse(Order order);

     @Mapping(target = "customerId", ignore = true)
     Order formCreateOrderRequestToOrder(CreateOrderRequest createOrderRequest);

     @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
     @Mapping(target = "orderLines", ignore = true)
     void formUpdateRequestToOrder(UpdateOrderRequest updateOrderRequest, @MappingTarget Order order);

     default BigDecimal calculateTotal(Order order) {
          if (order.getOrderLines() == null) return BigDecimal.ZERO;
          BigDecimal subtotal = order.getOrderLines().stream()
                  .map(line -> line.getUnitPrice().multiply(BigDecimal.valueOf(line.getQty())))
                  .reduce(BigDecimal.ZERO, BigDecimal::add);

          BigDecimal discountMultiplier = BigDecimal.ONE.subtract(
                  BigDecimal.valueOf(order.getDiscount() / 100.0)
          );
          return subtotal.multiply(discountMultiplier);
     }
}
