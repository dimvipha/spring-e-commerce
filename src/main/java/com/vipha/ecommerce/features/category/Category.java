package com.vipha.ecommerce.features.category;


import com.vipha.ecommerce.features.product.Product;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor

@Entity //for map table
@Table(name="categories")
public class Category {

    @Id // mark primary key of table
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // IDENTITY mean auto increment
    private Integer id;


    @Column(unique = true, nullable = false, length = 50)
    private  String name;


    @Column( nullable = false, length = 500)
    private String description;

    @Column(length = 255)
    private String icon;

    @Column(nullable = false)
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "parent_category_id")
    private Category parentCategory;

    @OneToMany(mappedBy = "category")
    private List<Product> products;


}
