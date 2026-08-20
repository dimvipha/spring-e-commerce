package com.vipha.ecommerce.features.order;

import com.vipha.ecommerce.features.order.dto.CreateOrderRequest;
import com.vipha.ecommerce.features.order.dto.OrderResponse;
import com.vipha.ecommerce.features.order.dto.UpdateOrderRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    @GetMapping
    public Page<OrderResponse> findAll(@RequestParam(required = false, defaultValue = "0") int pageNumber,
                                       @RequestParam(required = false, defaultValue = "10") int pageSize){
        return orderService.findAll(pageNumber,pageSize);
    }

    @GetMapping("/{id}")
    public OrderResponse findById(@PathVariable UUID id){
        return orderService.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createNew(@Valid @RequestBody CreateOrderRequest request,   @AuthenticationPrincipal Jwt jwt){
        orderService.createNew(request,jwt.getSubject());
    }

    @GetMapping("/me")
    public List<OrderResponse> findByCustomerId(@AuthenticationPrincipal Jwt jwt) {
        return orderService.getOrdersByCustomerId(jwt.getSubject());
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable UUID id){
        orderService.deleteById(id);
    }


    @PutMapping("/{id}")
    public void update(@PathVariable UUID id, @Valid @RequestBody UpdateOrderRequest request, @AuthenticationPrincipal Jwt jwt){
        orderService.update(id,request,jwt.getSubject());
    }

}
