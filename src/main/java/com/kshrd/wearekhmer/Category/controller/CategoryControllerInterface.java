package com.kshrd.wearekhmer.Category.controller;

import com.kshrd.wearekhmer.Category.model.Category;
import com.kshrd.wearekhmer.Category.model.CategoryRequestDTO;
import org.springframework.http.ResponseEntity;

public interface CategoryControllerInterface {

    ResponseEntity<?> getAllCategories() throws Exception;
    ResponseEntity<?> getCategoryById( String categoryId);
    ResponseEntity<?> insertCategory( CategoryRequestDTO category);
    ResponseEntity<?> updateCategory (Category category);
    ResponseEntity<?> deleteCategory(String categoryId);
}
