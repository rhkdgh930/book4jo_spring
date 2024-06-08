package com.booksajo.bookPanda.category.controller;

import com.booksajo.bookPanda.category.domain.Category;
import com.booksajo.bookPanda.category.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@AllArgsConstructor
public class CategoryController {


    private final CategoryService categoryService;


    @GetMapping("/api/category/{categoryId}")
    public ResponseEntity<Category> getCategoryById(@PathVariable(name = "categoryId") Long categoryId){
        Category category = categoryService.findCategoryById(categoryId);
        return ResponseEntity.ok(category);
    }


    @PostMapping("/api/category")
    public ResponseEntity<Category> addCategory(@RequestBody HashMap<String, Object> map){
            Category category = categoryService.createCategory(map.get("name").toString());

            return ResponseEntity.ok(category);
    }

    @PutMapping("/api/category/{categoryId}")
    public ResponseEntity<Category> updateCategory(@PathVariable(name = "categoryId") Long categoryId,@RequestBody HashMap<String, Object> map){
        Category target = categoryService.updateCategoryName(categoryId , map.get("name").toString());
        return ResponseEntity.ok(target);
    }


    @GetMapping("/api/category")
    public ResponseEntity<List<Category>> getAllCategory(){
        List<Category> categories = categoryService.findAllCategory();
        return ResponseEntity.ok(categories);
    }


    @DeleteMapping("/api/category/{categoryId}")
    public ResponseEntity<String> deleteCategoryById(@PathVariable(name = "categoryId") Long categoryId){
        categoryService.deleteCategoryById(categoryId);
        return ResponseEntity.ok("삭제 완료");
    }


}
