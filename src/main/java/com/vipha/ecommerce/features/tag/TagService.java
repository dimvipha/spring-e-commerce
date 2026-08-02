package com.vipha.ecommerce.features.tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TagService {

    TagResponse createNew(TagRequest request);

    TagResponse findById(Integer id);

    Page<TagResponse> findAllTag(Pageable pageable);

    TagResponse updateById(Integer id, TagRequest request);

    void deleteById(Integer id);
}
