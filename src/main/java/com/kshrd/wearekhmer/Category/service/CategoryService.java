package com.kshrd.wearekhmer.Category.service;

import com.kshrd.wearekhmer.Category.model.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategories();
    Category getCategoryById( String categoryId);

    Category insertCategory( Category category);

    Category updateCategory(Category category);

    Category deleteCategory(String categoryId);


}
