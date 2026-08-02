package com.vipha.ecommerce.features.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{
    boolean existsByName(String name);

    // auto generated query
    Optional<Category> findByName(String name);

}
