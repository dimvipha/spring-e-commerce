package com.vipha.ecommerce.features.category;

import com.vipha.ecommerce.features.category.dto.CategoryResponse;
import com.vipha.ecommerce.features.category.dto.CreateCategoryRequest;
import com.vipha.ecommerce.features.category.dto.UpdateCategoryRequest;
import com.vipha.ecommerce.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponse findById(Integer id){
        return categoryRepository.findById(id).map(
                categoryMapper::mapCategoryToCategoryResponse
        ).orElseThrow(
                ()->new ResponseStatusException(HttpStatus.NOT_FOUND, "category has not been found")
        );

//        return categoryMapper.mapCategoryToCategoryResponse(category);
    }


    @Override
    public void deleteById(Integer id){
        // validate category
        Category category =categoryRepository.findById(id).orElseThrow(
                ()->new ResponseStatusException(HttpStatus.NOT_FOUND, "category has not been found")
        );
        categoryRepository.delete(category);
    }

    @Override
    public CategoryResponse updateById(Integer id, UpdateCategoryRequest request){

        // validate category
        Category category =categoryRepository.findById(id).orElseThrow(
                ()->new ResponseStatusException(HttpStatus.NOT_FOUND, "category has not been found")
        );

        // validate category name
        if(categoryRepository.existsByName(request.name())){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"category has already exits");
        }

        categoryMapper.toEntity(request,category);
        category=categoryRepository.save(category);

        // map to categoryResponse
        return categoryMapper.mapCategoryToCategoryResponse(category);

    }

    @Override
    public Page<CategoryResponse> findAllCategory(Pageable pageable){
//        Pageable pageable= PageRequest.of(0,25);
        Page<Category> categories=categoryRepository.findAll(pageable);

        return categories.map(categoryMapper::mapCategoryToCategoryResponse);
    }

    @Override
    public CategoryResponse createNew(CreateCategoryRequest createCategoryRequest) {
        // TODO:
        // 1. Validate all information form DTO
        // Validate category name (unique)
        Optional<Category> category = categoryRepository.findByName(createCategoryRequest.name());

        if (category.isPresent()) {
//            System.out.println("Category already exists");
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Category name already exits"
            );
        }

        Category newCategory = categoryMapper.mapCreateCategoryRequestToCategory(createCategoryRequest);
        newCategory.setIsDeleted(false);

        // Validate parent category id
        if (createCategoryRequest.parentCategoryId() != null) {
            Category parentCategory = categoryRepository
                    .findById(createCategoryRequest.parentCategoryId())
                    .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Parent category not found"));
            newCategory.setParentCategory(parentCategory);
        }

        newCategory = categoryRepository.save(newCategory);

        return categoryMapper.mapCategoryToCategoryResponse(newCategory);
    }

}
