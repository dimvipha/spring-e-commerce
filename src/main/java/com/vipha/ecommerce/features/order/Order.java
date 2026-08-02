package com.vipha.ecommerce.features.order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.UUID;


@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String customerId;

    @Column(nullable = false)
    private float discount;


    @Column(nullable = false)
    private Instant orderedAt;

    private String remark;

    @Column(nullable = false)
    private Boolean status;

    @Column(nullable = false)
    private Boolean isDeleted;

    @OneToMany(mappedBy = "order")
    private List<OrderLine> orderLines;

}
