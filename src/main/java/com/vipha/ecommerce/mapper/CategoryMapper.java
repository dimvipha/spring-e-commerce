package com.vipha.ecommerce.mapper;


import com.vipha.ecommerce.features.category.dto.CategoryResponse;
import com.vipha.ecommerce.features.category.dto.CreateCategoryRequest;
import com.vipha.ecommerce.features.category.dto.UpdateCategoryRequest;
import com.vipha.ecommerce.features.category.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

// covert interface into mapStruct
@Mapper(componentModel = "spring")
public interface CategoryMapper {

    // what is source ?=> parameter
    // what is target ? => return
     Category mapCreateCategoryRequestToCategory(CreateCategoryRequest request);

     void toEntity(UpdateCategoryRequest dto, @MappingTarget Category category);

     @Mapping(source = "name", target = "cateName")
     CategoryResponse mapCategoryToCategoryResponse(Category category);
}
