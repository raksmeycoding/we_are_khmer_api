package com.kshrd.wearekhmer.Category.repository;


import com.kshrd.wearekhmer.Category.model.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface CategoryMapper {

    @Select("SELECT * FROM category")
    @Results({
            @Result(property = "categoryId", column = "category_id"),
            @Result(property = "categoryName", column = "category_name"),
            @Result(property = "categoryImage", column = "category_image")
    })
    List<Category> getAllCategories();

    @Select("SELECT * FROM category WHERE category_id = #{categoryId}")
    @Results(
            id = "categoryMap",
            value = {
                    @Result(property = "categoryId", column = "category_id"),
                    @Result(property = "categoryName", column = "category_name"),
                    @Result(property = "categoryImage", column = "category_image")
            }
    )
    Category getCategoryById(@Param("categoryId") String categoryId);

    @Select("""
            INSERT INTO category (category_name) VALUES (#{category.categoryName}) returning *
            """)
    @ResultMap("categoryMap")
    Category insertCategory(@Param("category") Category category);

    @Select("UPDATE category SET category_name = #{category.categoryName}, " +
            "category_image = #{category.categoryImage} WHERE category_id = #{category.categoryId} returning *")
    @ResultMap("categoryMap")
    Category updateCategory(@Param("category") Category category);

    @Select("DELETE FROM category WHERE category_id = #{categoryId} returning *")
    @ResultMap("categoryMap")
    Category deleteCategory(@Param("categoryId") String categoryId);
}

