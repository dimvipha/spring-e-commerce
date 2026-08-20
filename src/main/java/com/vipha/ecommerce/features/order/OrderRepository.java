package com.vipha.ecommerce.features.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    @EntityGraph(attributePaths = {"orderLines", "orderLines.product"})
    Optional<Order> findById(UUID id);

    @EntityGraph(attributePaths = {"orderLines", "orderLines.product"})
    List<Order> findByCustomerId(String customerId);

    @EntityGraph(attributePaths = {"orderLines", "orderLines.product"})
    Page<Order> findAll(Pageable pageable);
}
