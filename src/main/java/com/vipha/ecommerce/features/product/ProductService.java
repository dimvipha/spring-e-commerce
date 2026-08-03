package com.vipha.ecommerce.features.product;

import com.vipha.ecommerce.features.product.dto.CreateProductRequest;
import com.vipha.ecommerce.features.product.dto.ProductResponse;
import com.vipha.ecommerce.features.product.dto.UpdateProductRequest;
import org.springframework.data.domain.Page;

public interface ProductService {

    void createNew(CreateProductRequest request);
//    Page<ProductResponse> findAll(Pageable pageable);
    Page<ProductResponse> findAll(int pageNumber, int pageSize);
    ProductResponse findById(Integer id);
    ProductResponse findByCode(String code);

    ProductResponse patchById(Integer id,UpdateProductRequest request);
    Page<ProductResponse> search(String name, int pageNumber, int pageSize);

}
