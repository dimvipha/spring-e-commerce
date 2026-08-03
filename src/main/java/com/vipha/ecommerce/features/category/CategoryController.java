package com.vipha.ecommerce.features.category;


import com.vipha.ecommerce.features.category.dto.CategoryResponse;
import com.vipha.ecommerce.features.category.dto.CreateCategoryRequest;
import com.vipha.ecommerce.features.category.dto.UpdateCategoryRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Integer id){
        categoryService.deleteById(id);
    }

    @GetMapping("/{id}")
    public CategoryResponse findById(@PathVariable Integer id){
       return categoryService.findById(id);
    }

    @GetMapping
    public Page<CategoryResponse> findAll(
            @RequestParam(required = false , defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "10")  int pageSize
    ){
        Sort sortById= Sort.by(Sort.Direction.DESC,"id");
        Pageable pageable= PageRequest.of(pageNumber, pageSize,sortById);
        return categoryService.findAllCategory(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public  CategoryResponse createCategory(@Valid @RequestBody CreateCategoryRequest request){
        return categoryService.createNew(request);
    }

    @PutMapping("/{id}")
    public CategoryResponse updateCategory(@PathVariable Integer id, @Valid @RequestBody  UpdateCategoryRequest request){
        return categoryService.updateById(id, request);
    }

    @GetMapping("/search")
    public List<CategoryResponse> search(@RequestParam(required = false,defaultValue = "") String name){
        return categoryService.search(name);
    }

}
