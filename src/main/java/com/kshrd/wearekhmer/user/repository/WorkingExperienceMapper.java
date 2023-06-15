package com.kshrd.wearekhmer.user.repository;

import com.kshrd.wearekhmer.user.model.entity.WorkingExperience;
import com.kshrd.wearekhmer.user.model.entity.WorkingExperienceResponse;
import lombok.Builder;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface WorkingExperienceMapper {

    @Select("select * from working_experience_tb")
    @Results(
            id = "workingExperienceMap",
            value = {
                    @Result(property = "workingExperienceId", column = "wId"),
                    @Result(property = "workingExperienceName", column = "w_name"),
//                    @Result(property = "userId", column = "user_id")
            }

    )
    List<WorkingExperienceResponse> getAll();

    @Select("INSERT INTO working_experience_tb (w_name, user_id) VALUES (#{workingExperienceName}, #{userId}) returning *")
//    @ResultMap("workingExperienceMap")
    @Result(property = "workingExperienceId", column = "wid" )
    @Result(property = "workingExperienceName", column = "w_name")
    @Result(property = "userId", column = "user_id")
    WorkingExperience insert(WorkingExperience workingExperience);


    @Select("SELECT * FROM working_experience_tb WHERE wId = #{workingExperienceId}")
    @ResultMap("workingExperienceMap")
    WorkingExperience getById(String workingExperienceId);

    @Select("select * from working_experience_tb where user_id = #{userId}")
    @ResultMap("workingExperienceMap")
    WorkingExperience getByUserId(String userId);


    @Select("""
            select cast(w_name as varchar) as w_name from working_experience_tb where user_id = #{userId}
            """)
    WorkingExperienceResponse getWorkingByUserId(String userId);

    @Select("UPDATE working_experience_tb SET w_name = #{workingExperienceName} WHERE wId = #{workingExperienceId} RETURNING *")
    @ResultMap("workingExperienceMap")
    WorkingExperience update(WorkingExperience workingExperience);

    @Select("DELETE FROM working_experience_tb WHERE wId = #{workingExperienceId} RETURNING *")
    @ResultMap("workingExperienceMap")
    WorkingExperience delete(String workingExperienceId);



    @Delete("""
            delete from working_experience_tb where user_id = #{userId}
            """)
    void deleteAllWorkingExperienceIfUserIdExist(String userId);
}

