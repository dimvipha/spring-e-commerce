package com.vipha.ecommerce.features.product;

import com.vipha.ecommerce.features.order.OrderLine;
import com.vipha.ecommerce.features.tag.Tag;
import com.vipha.ecommerce.features.category.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name="products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String code;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private Boolean isAvailable;

    @Column(nullable = false)
    private Boolean isDeleted;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private  Integer qty;

    @Column(nullable = false)
    private String slug;

    @Column(nullable = false)
    private  String  thumbnail;

    @Column(nullable = false)
    private BigDecimal unitPrice;

    @ManyToOne
    private Category category;

    @OneToMany
    private List<OrderLine> orderLines;


    @ManyToMany
    @JoinTable(name = "products_tags_data",
           joinColumns = @JoinColumn(name="product_id"),
            inverseJoinColumns = @JoinColumn(name="tag_id")
    )
    private List<Tag> tags;
}
