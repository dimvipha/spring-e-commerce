package com.vipha.ecommerce.features.order;

import com.vipha.ecommerce.features.order.dto.CreateOrderRequest;
import com.vipha.ecommerce.features.order.dto.OrderResponse;
import com.vipha.ecommerce.features.order.dto.UpdateOrderRequest;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    Page<OrderResponse> findAll(int pageNumber, int pageSize);
    OrderResponse findById(UUID id);
    void createNew(CreateOrderRequest request,String customerId);
    void update(UUID id,UpdateOrderRequest request, String customerId);
    List<OrderResponse> getOrdersByCustomerId(String customerId);
    void deleteById(UUID id);
}
