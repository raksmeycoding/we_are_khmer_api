package com.kshrd.wearekhmer.Category.repository;


import com.kshrd.wearekhmer.Category.model.Category;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CategoryRepository {


    @Select("""
            insert into category(category_name) values (#{p})
            """)
    @Results(
            id = "categoryMap",
            value = {
                    @Result(property = "id", column = "category_id"),
                    @Result(property = "name", column = "category_name"),
                    @Result(property = "categoryUrl", column = "category_url")

            }
    )
    Category createCategory(@Param("p") String categoryName);


}
