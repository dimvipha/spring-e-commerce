package com.vipha.ecommerce.features.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    List<Tag> findByNameContainsIgnoreCase(String name);

    boolean existsByName(String name);

    // auto generated query
    Optional<Tag> findByName(String name);
}
