package com.vipha.ecommerce.features.order;

import com.vipha.ecommerce.features.order.dto.*;
import com.vipha.ecommerce.features.product.Product;
import com.vipha.ecommerce.features.product.ProductRepository;
import com.vipha.ecommerce.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private  final OrderRepository orderRepository;
    private  final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    @Override
    public Page<OrderResponse> findAll(int pageNumber, int pageSize) {
        Pageable pageable= PageRequest.of(pageNumber, pageSize);
        return orderRepository.findAll(pageable).map(orderMapper::toOrderResponse);
    }

    @Override
    public OrderResponse findById(UUID id) {
        Order order= orderRepository.findById(id).orElseThrow(
                ()->new ResponseStatusException(
                        HttpStatus.NOT_FOUND,"Order not found"
                )
        );
        return orderMapper.toOrderResponse(order);
    }

    @Transactional
    @Override
    public void createNew(CreateOrderRequest request,String customerId) {
//        Service (business rules — needs DB / cross-field logic):
//        1. no duplicate productIds across order lines
        Set<Integer> productIds = request.orderLines().stream()
                .map(CreateOrderLineRequest::productId)
                .collect(Collectors.toSet());

        if (new HashSet<>(productIds).size() != productIds.size()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Order contains duplicate products"
            );
        }

//        2. fetch all products in one query → Map<UUID, Product>
        Map<Integer, Product> validProducts = request.orderLines().stream()
                .map(line -> productRepository.findById(line.productId())
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Product not found: " + line.productId()
                        ))
                )
                .collect(Collectors.toMap(Product::getId, Function.identity()));


//        3. every productId exists in DB
        request.orderLines().stream()
                .map(line -> validProducts.get(line.productId()))
                .forEach(product -> {
                    if (product.getIsDeleted()) {
                        throw new ResponseStatusException(
                                HttpStatus.BAD_REQUEST, "Product is no longer available: " + product.getName());
                    }
                });

//       4. product not deleted/inactive
        Order newOrder = orderMapper.formCreateOrderRequestToOrder(request);
        newOrder.setCustomerId(customerId);
        newOrder.setOrderedAt(Instant.now());
        newOrder.setStatus(false);
        newOrder.setIsDeleted(false);

//        5. sufficient stock per line
        List<OrderLine> orderLines = request.orderLines().stream()
                .map(line -> {
                    Product product = validProducts.get(line.productId());

                    OrderLine orderLine = new OrderLine();
                    orderLine.setProduct(product);
                    orderLine.setQty(line.qty());
                    orderLine.setOrder(newOrder);
                    orderLine.setUnitPrice(product.getUnitPrice());
                    return orderLine;
                })
                .toList();

        newOrder.setOrderLines(orderLines);
//        6. build Order + OrderLines, save
        Order saved = orderRepository.save(newOrder);
        orderMapper.toOrderResponse(saved);

    }

    @Override
    @Transactional
    public void update(UUID id, UpdateOrderRequest request, String customerId) {

        Order order = orderRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found")
        );

        if (!order.getCustomerId().equals(customerId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You cannot update another customer's order");
        }

        if (order.getIsDeleted()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order is deleted");
        }

        List<Integer> productIds = request.orderLines().stream()
                .map(UpdateOrderLineRequest::productId)
                .toList();

        Map<Integer, Product> validProducts = productRepository.findAllById(productIds).stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));

        if (!validProducts.keySet().containsAll(productIds)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "One or more products not found");
        }

        // map simple fields (address, discount, remark, etc.) — orderLines is ignored by the mapper now
        orderMapper.formUpdateRequestToOrder(request, order);

        // replace order lines manually, since orphanRemoval=true will delete the old ones
        List<OrderLine> newLines = request.orderLines().stream()
                .map(lineRequest -> {
                    Product product = validProducts.get(lineRequest.productId());

                    OrderLine line = new OrderLine();
                    line.setProduct(product);
                    line.setOrder(order);
                    line.setQty(lineRequest.qty());
                    line.setUnitPrice(product.getUnitPrice());
                    return line;
                })
                .toList();

        order.getOrderLines().clear();   // triggers orphanRemoval on old lines
        order.getOrderLines().addAll(newLines);

        orderRepository.save(order);
    }

    @Override
    public List<OrderResponse> getOrdersByCustomerId(String customerId) {
        List<Order> orders = orderRepository.findByCustomerId(customerId);

        if (orders.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No orders found");
        }

        return orders.stream()
                .map(orderMapper::toOrderResponse)
                .toList();
    }
    @Override
    public void deleteById(UUID id) {
        Order order= orderRepository.findById(id).orElseThrow(
                ()->new ResponseStatusException(
                        HttpStatus.NOT_FOUND,"Order not found"
                )
        );
        order.setIsDeleted(Boolean.TRUE);
        orderRepository.save(order);
    }
}
