package com.kshrd.wearekhmer.Category.controller;


import com.kshrd.wearekhmer.Category.model.Category;
import com.kshrd.wearekhmer.Category.model.CategoryRequestDTO;
import com.kshrd.wearekhmer.Category.service.CategoryService;
import com.kshrd.wearekhmer.exception.CustomRuntimeException;
import com.kshrd.wearekhmer.requestRequest.GenericResponse;
import com.kshrd.wearekhmer.utils.validation.WeAreKhmerValidation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
public class CategoryController implements CategoryControllerInterface {

    private final CategoryService categoryService;

    private WeAreKhmerValidation weAreKhmerValidation;


    @Override
    @GetMapping
    public ResponseEntity<?> getAllCategories() throws Exception {
        System.out.println(SecurityContextHolder.getContext().getAuthentication());

        GenericResponse genericResponse;
        try {
            List<Category> categories = categoryService.getAllCategories();
            genericResponse =
                    GenericResponse.builder()
                            .status("200")
                            .message("success")
                            .payload(categories)
                            .build();
            return ResponseEntity.ok(genericResponse);
        } catch (Exception e) {
//            System.out.println(e.getMessage());
            genericResponse =
                    GenericResponse.builder()
                            .status("500")
                            .title("internal server error")
                            .message(e.getMessage())
                            .build();
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(genericResponse);
        }
    }

    @Override
    @GetMapping("{categoryId}")
    public ResponseEntity<?> getCategoryById(@PathVariable String categoryId) {
        GenericResponse genericResponse;
        try {
            Category category = categoryService.getCategoryById(categoryId);
            genericResponse =
                    GenericResponse.builder()
                            .status("200")
                            .title("success")
                            .payload(category)
                            .build();

            return ResponseEntity.ok(genericResponse);
        } catch (Exception e) {
            e.printStackTrace();
            genericResponse =
                    GenericResponse.builder()
                            .status("500")
                            .title("internal server error")
                            .message(e.getMessage())
                            .build();
            return ResponseEntity.internalServerError().body(genericResponse);
        }
    }

    @PostMapping

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "(Only administrator can create this category.)")
    public ResponseEntity<?> insertCategory(CategoryRequestDTO category) {
        if (!weAreKhmerValidation.isAdmin()) {
            throw new CustomRuntimeException("You are not administrator.");
        }

        Category category1 = Category.builder()
                .categoryName(category.getCategoryName())
                .build();
        GenericResponse genericResponse;
        try {
            Category category2 = categoryService.insertCategory(category1);
            genericResponse =
                    GenericResponse.builder()
                            .status("200")
                            .title("success")
                            .payload(category2)
                            .build();

            return ResponseEntity.ok(genericResponse);
        } catch (Exception e) {
            e.printStackTrace();
            genericResponse =
                    GenericResponse.builder()
                            .status("500")
                            .title("internal server error")
                            .message(e.getMessage())
                            .build();
            return ResponseEntity.internalServerError().body(genericResponse);
        }
    }

    @Override
    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "(Only administrator can update this category.)")
    public ResponseEntity<?> updateCategory(Category category) {
        if (!weAreKhmerValidation.isAdmin()) {
            throw new CustomRuntimeException("You are not administrator.");
        }
        GenericResponse genericResponse;
        try {
            Category category2 = categoryService.updateCategory(category);
            genericResponse =
                    GenericResponse.builder()
                            .status("200")
                            .title("success")
                            .payload(category2)
                            .build();

            return ResponseEntity.ok(genericResponse);
        } catch (Exception e) {
            e.printStackTrace();
            genericResponse =
                    GenericResponse.builder()
                            .status("500")
                            .title("internal server error")
                            .message(e.getMessage())
                            .build();
            return ResponseEntity.internalServerError().body(genericResponse);
        }
    }

    @Override
    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "(Only administrator can delete this category.)")
    public ResponseEntity<?> deleteCategory(String categoryId) {
        if (!weAreKhmerValidation.isAdmin()) {
            throw new CustomRuntimeException("You are not administrator.");
        }
        GenericResponse genericResponse;
        try {
            Category category2 = categoryService.deleteCategory(categoryId);
            genericResponse =
                    GenericResponse.builder()
                            .status("200")
                            .title("success")
                            .payload(category2)
                            .build();

            return ResponseEntity.ok(genericResponse);
        } catch (Exception e) {
            e.printStackTrace();
            genericResponse =
                    GenericResponse.builder()
                            .status("500")
                            .title("internal server error")
                            .message(e.getMessage())
                            .build();
            return ResponseEntity.internalServerError().body(genericResponse);
        }
    }
}
