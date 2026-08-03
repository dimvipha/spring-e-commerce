package com.vipha.ecommerce.features.category;

import com.vipha.ecommerce.features.category.dto.CreateCategoryRequest;
import com.vipha.ecommerce.features.category.dto.CategoryResponse;
import com.vipha.ecommerce.features.category.dto.UpdateCategoryRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {

    Page<CategoryResponse> findAllCategory(Pageable pageable);
     CategoryResponse findById(Integer id);

    CategoryResponse updateById(Integer id, UpdateCategoryRequest request);

    CategoryResponse createNew(CreateCategoryRequest categoryRequest);

    void deleteById(Integer id);

    List<CategoryResponse> search(String name);

}
