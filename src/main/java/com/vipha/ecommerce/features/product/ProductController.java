package com.vipha.ecommerce.features.product;

import com.vipha.ecommerce.features.product.dto.CreateProductRequest;
import com.vipha.ecommerce.features.product.dto.ProductResponse;
import com.vipha.ecommerce.features.product.dto.UpdateProductRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private  final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createNew(@Valid @RequestBody CreateProductRequest request){
        productService.createNew(request);
    }

    @GetMapping("/code/{code}")
    public ProductResponse findByCode(@PathVariable String code){
        return productService.findByCode(code);
    }

    @GetMapping("/{id}")
    public ProductResponse findById(@PathVariable Integer id){
        return productService.findById(id);
    }

    @PatchMapping("/{id}")
    public ProductResponse patchById(@PathVariable Integer id,@Valid @RequestBody UpdateProductRequest updateProductRequest ){
        return productService.patchById(id, updateProductRequest);
    }

    @GetMapping
    public  Page<ProductResponse> findAll(@RequestParam(required = false, defaultValue = "0") int pageNumber,
                                  @RequestParam(required = false, defaultValue = "10") int pageSize
    ){
        return productService.findAll(pageNumber,pageSize);
    }

    @GetMapping("/search")
    public  Page<ProductResponse> search(@RequestParam(required = false,defaultValue = "") String name, @RequestParam(required = false, defaultValue = "0") int pageNumber,
                                          @RequestParam(required = false, defaultValue = "10") int pageSize
    ){
        return productService.search(name,pageNumber,pageSize);
    }



//    @GetMapping
//    public Page<ProductResponse> findAll(@RequestParam(required = false, defaultValue = "0") int pageNumber,
//                                  @RequestParam(required = false, defaultValue = "10") int pageSize
//                                  ){
//        Sort sortById=Sort.by(Sort.Direction.DESC,"id");
//        Pageable pageable= PageRequest.of(pageNumber, pageSize, sortById);
//
//        return productService.findAll(pageable);
//    }

}
