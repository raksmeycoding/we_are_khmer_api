package com.kshrd.wearekhmer.Category.controller;


import com.kshrd.wearekhmer.Category.model.Category;
import com.kshrd.wearekhmer.Category.model.CategoryRequestDTO;
import com.kshrd.wearekhmer.Category.repository.CategoryMapper;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/category")
@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    private WeAreKhmerValidation weAreKhmerValidation;


    private final CategoryMapper categoryMapper;


    private static final Integer PAGE_SIZE = 10;


    private Integer getNextPageCategory(Integer page) {
        int numberOfRecord = categoryMapper.getTotalCategoryRecord();
        int totalPage = (int) Math.ceil((double) numberOfRecord / PAGE_SIZE);
        if (page > totalPage) {
            page = totalPage;
        }
        weAreKhmerValidation.validatePageNumber(page);
        return (page - 1) * PAGE_SIZE;
    }


    @GetMapping
    public ResponseEntity<?> getAllCategories(@RequestParam(value = "page", required = false) Integer page) throws Exception {
        System.out.println(SecurityContextHolder.getContext().getAuthentication());

        GenericResponse genericResponse;
        try {
            if (page != null) {
                Integer nextPage = getNextPageCategory(page);
                Map<String, Object> response = new LinkedHashMap<>();
                List<Category> paginateCategory = categoryService.getAllCategoryWithPaginate(PAGE_SIZE, nextPage);
                response.put("totalRecords", categoryMapper.getTotalCategoryRecord());
                response.put("message", "Get category successfully." );
                response.put("title", "success");
                response.put("status", "200");
                response.put("payload", paginateCategory);
                return ResponseEntity.ok().body(response);
            }
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


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "(Only administrator can create this category.)")
    public ResponseEntity<?> insertCategory(@RequestBody @Validated CategoryRequestDTO category) {
        if (!weAreKhmerValidation.isAdmin()) {
            throw new CustomRuntimeException("You are not administrator.");
        }

        Category category1 = Category.builder()
                .categoryImage(category.getCategoryImage())
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


    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "(Only administrator can update this category.)")
    public ResponseEntity<?> updateCategory(@RequestBody @Validated Category category) {
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
