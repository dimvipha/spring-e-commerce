package com.vipha.ecommerce.features.product;

import com.vipha.ecommerce.features.product.dto.CreateProductRequest;
import com.vipha.ecommerce.features.product.dto.ProductResponse;
import com.vipha.ecommerce.features.product.dto.UpdateProductRequest;
import com.vipha.ecommerce.features.category.Category;
import com.vipha.ecommerce.features.tag.Tag;
import com.vipha.ecommerce.mapper.ProductMapper;
import com.vipha.ecommerce.features.category.CategoryRepository;
import com.vipha.ecommerce.features.tag.TagRepository;
import com.vipha.ecommerce.utils.GenerateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private  final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponse patchById(Integer id,UpdateProductRequest updateProductRequest){
        Product validProduct=productRepository.findById(id).orElseThrow(
                ()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Product Id has not been found"
                )
        );


        // validate category id if client patch
        if(updateProductRequest.categoryId()!=null){
            Category category=categoryRepository.findById(updateProductRequest.categoryId()).orElseThrow(
                    ()->new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Category Id has not been found"
                    )
            );
            validProduct.setCategory(category);

        }

        // validate tag id if client patch
        if(updateProductRequest.tagIds()!=null){
            List<Tag> validTags=updateProductRequest.tagIds().stream()
                    .map(
                            tagId->tagRepository.findById(tagId).orElseThrow(
                                    ()-> new ResponseStatusException(
                                            HttpStatus.NOT_FOUND,
                                            "Tag Id that you have provided not been found"
                                    )
                            )
                    ).collect(Collectors.toList());

            validProduct.setTags(validTags);
        }

       productMapper.toEntity(updateProductRequest, validProduct);
        productRepository.save(validProduct);

        return productMapper.toProductResponse(validProduct);
    }


    @Override
    public ProductResponse findByCode(String code){
        Product products= productRepository.findByCode(code).orElseThrow(
                ()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Product code has not been found"
                )
        );
        return productMapper.toProductResponse(products);
    }

    @Override
    public ProductResponse findById(Integer id){
        Product products= productRepository.findById(id).orElseThrow(
                ()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Product Id has not been found"
                )
        );

        return productMapper.toProductResponse(products);
    }

    @Override
    public Page<ProductResponse> findAll(int pageNumber, int pageSize){
        Sort sortById=Sort.by(Sort.Direction.DESC,"id");
        Pageable pageable= PageRequest.of(pageNumber, pageSize,sortById);

        return productRepository.findAll(pageable).map(productMapper::toProductResponse);
    }

//    @Override
//    public Page<ProductResponse> findAll(Pageable pageable){
//        Page<Product> products=productRepository.findAll(pageable);
//        return products.map(productMapper::toProductResponse);
//    }

    @Override
    public void createNew(CreateProductRequest createProductRequest){
        Category validCategory=categoryRepository.findById(
                createProductRequest.categoryId()).orElseThrow(
                ()->new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Category not found"
                )
        );

        List<Tag> validTag=createProductRequest.tagIds().stream().map(
                tagIds->tagRepository.findById(tagIds).orElseThrow(
                        ()->new  ResponseStatusException(
                                HttpStatus.NOT_FOUND,"Tags Id has not been found"
                        )
                )
        ).toList();

        Product newProducts=productMapper.toProductEntity(createProductRequest);
        newProducts.setCode(GenerateUtils.getProductCode());
        newProducts.setSlug(GenerateUtils.toSlug(newProducts.getName()));
        newProducts.setCategory(validCategory);
        newProducts.setTags(validTag);
        newProducts.setIsDeleted(false);

        productRepository.save(newProducts);





    }
}
