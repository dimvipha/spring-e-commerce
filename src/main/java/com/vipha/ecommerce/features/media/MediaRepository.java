package com.vipha.ecommerce.features.media;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MediaRepository extends JpaRepository<Media,Integer> {
    Optional<Media> findByName(String name);
    Page<Media> findByIsDraft(Pageable pageable,Boolean isDraft);
}
