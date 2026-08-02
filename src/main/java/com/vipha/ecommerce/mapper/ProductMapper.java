package com.vipha.ecommerce.mapper;

import com.vipha.ecommerce.features.product.dto.CreateProductRequest;
import com.vipha.ecommerce.features.product.dto.ProductResponse;
import com.vipha.ecommerce.features.product.dto.UpdateProductRequest;
import com.vipha.ecommerce.features.product.Product;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

// covert interface into mapStruct
@Mapper(componentModel = "spring",uses = {
        CategoryMapper.class
})
public interface ProductMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toEntity(UpdateProductRequest updateProductRequest,@MappingTarget Product product);
    Product toProductEntity(CreateProductRequest request);
    ProductResponse toProductResponse(Product product);

}
