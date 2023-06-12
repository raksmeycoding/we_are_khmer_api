package com.kshrd.wearekhmer.user.repository;

import com.kshrd.wearekhmer.user.model.entity.Education;
import com.kshrd.wearekhmer.user.model.entity.EducationResponse;
import com.kshrd.wearekhmer.user.model.entity.Quote;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface EducationMapper {

    @Select("SELECT * FROM education")
    @Results(
            id = "educationMapperId",
            value = {
                    @Result(property = "educationId", column = "e_id"),
                    @Result(property = "educationName", column = "e_name"),
//                    @Result(property = "userId", column = "user_id")
            }
    )
    List<EducationResponse> getAll();

    @Select("SELECT * FROM education WHERE e_id = #{educationId}")
    @ResultMap("educationMapperId")
    Education getById(String educationId);

    @Select("""
            select education.e_name as edu_name from education where education.user_id = #{userId}
            """)
    String getEducationByUserIdAsString(String userId);

    @Select("""
            select * from education where education.user_id = #{userId}
            """)
    @ResultMap("educationMapperId")
    EducationResponse getEducationByUserIdObject(String userId);

    @Select("INSERT INTO education (e_name, user_id) VALUES (#{educationName}, #{userId}) returning *")
    @ResultMap("educationMapperId")
    Education insert(Education education);

    @Select("UPDATE education SET e_name = #{educationName} WHERE e_id = #{educationId} returning *")
    @ResultMap("educationMapperId")
    Education update(Education education);

    @Select("DELETE FROM education WHERE e_id = #{educationId} RETURNING *")
    @ResultMap("educationMapperId")
    Education delete(String educationId);


    @Delete("""
            delete from education where user_id = #{userId}
            """)
    void deleteAllEducationIfUserIdExist(String userId);
}


//    create table education
//        (
//                e_id varchar primary key default uuid_generate_v4(),
//    e_name varchar not null,
//        user_id varchar references user_tb(user_id) on delete cascade on update cascade
//        );
//
//public class Education {
//    private String educationId;
//    private String educationName;
//    private String userId;
//}
//
//    please write mybatis annotation no using xml to map education table to Education. and have some operation like getAllEducation, getEducationById and all of return operation return as Education