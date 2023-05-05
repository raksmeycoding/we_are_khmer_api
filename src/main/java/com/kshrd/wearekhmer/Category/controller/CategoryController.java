package com.kshrd.wearekhmer.Category.controller;


import com.kshrd.wearekhmer.Category.model.Category;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {



    @GetMapping("/add")
    public Category addCategory(@RequestBody String name) {
     return null;
    };
}
