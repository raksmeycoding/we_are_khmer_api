package com.kshrd.wearekhmer.userArtivities.repository;

import com.kshrd.wearekhmer.userArtivities.model.navbar.Navbar;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NavbarMapper {



    @Select("""
            update navbar_tb set navbar_name = #{navbar_name}, order_number = #{order_number} where category_id = #{category_id} returning *
            """)
    Navbar updateNavbar(Navbar navbar);



    @Select("""
            select * from navbar_tb order by order_number
            """)
    List<Navbar> getNavbar();
}
