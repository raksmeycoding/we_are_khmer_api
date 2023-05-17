package com.kshrd.wearekhmer.Category.service;


import com.kshrd.wearekhmer.Category.model.Category;
import com.kshrd.wearekhmer.Category.repository.CategoryMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class CategoryServiceImp implements CategoryService{
    private final CategoryMapper categoryMapper;

    @Override
    public List<Category> getAllCategories() {
        return categoryMapper.getAllCategories();
    }

    @Override
    public Category getCategoryById(String categoryId) {
        return categoryMapper.getCategoryById(categoryId);
    }

    @Override
    public Category insertCategory(Category category) {
        return categoryMapper.insertCategory(category);
    }

    @Override
    public Category updateCategory(Category category) {
        return categoryMapper.updateCategory(category);
    }

    @Override
    public Category deleteCategory(String categoryId) {
        return categoryMapper.deleteCategory(categoryId);
    }
}
