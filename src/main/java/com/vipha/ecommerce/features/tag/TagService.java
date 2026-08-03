package com.vipha.ecommerce.features.tag;

import com.vipha.ecommerce.features.tag.dto.TagRequest;
import com.vipha.ecommerce.features.tag.dto.TagResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {

    TagResponse createNew(TagRequest request);

    TagResponse findById(Integer id);

    Page<TagResponse> findAllTag(Pageable pageable);

    TagResponse updateById(Integer id, TagRequest request);

    void deleteById(Integer id);

    List<TagResponse> search(String name);
}
