package com.vipha.ecommerce;

import com.vipha.ecommerce.features.category.Category;
import com.vipha.ecommerce.features.category.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ECommerceApplicationTests {
    // save data into category data

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void test_saveCategory(){
        Category category=new Category();
        category.setName("phone");
        category.setIcon("17promax-icon.svg");
        category.setDescription("hello");
        category.setIsDeleted(false);

        categoryRepository.save(category);
    }

    @Test
    void test_getCategory(){
        List<Category> categories= categoryRepository.findAll();
        IO.println("__________________ categories _________________");
        categories.forEach(category -> {
            IO.println("ID: "+category.getId());
            IO.println("name: "+category.getName());
            IO.println("icon: "+category.getIcon());
            IO.println("isDeleted: "+category.getIsDeleted());
            IO.println("_____________________________________________");
        });
    }



//    @Test
//    void contextLoads() {
//    }

}
