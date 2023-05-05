package com.kshrd.wearekhmer.Category.service;

import com.kshrd.wearekhmer.Category.model.Category;
import com.kshrd.wearekhmer.Category.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class CategoryServiceImp implements CategoryService{

    private final CategoryRepository categoryRepository;
    @Override
    public Category createCategory(String categoryName) {
        return categoryRepository.createCategory(categoryName);
    }
}
