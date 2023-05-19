package com.kshrd.wearekhmer.userArtivities.repository;

import com.kshrd.wearekhmer.userArtivities.model.React;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface IReactRepository {
    @Select("""
            select handle_user_like(#{userId}, #{articleId}, 'like')
            """)
    @Results(id = "reactMap"
            , value = {
            @Result(property = "reactId", column = "react_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "articleId", column = "article_id"),
            @Result(property = "status", column = "handle_user_like"),
            @Result(property = "createAt", column = "createAt"),
            @Result(property = "updateAt", column = "updateAt")
    })
    React createUserReactForCurrentUser(React react);


    @Select("""
            select handle_user_like(#{userId}, #{articleId}, 'unlike');
            """)
    React deleteUserReactForCurrentUser(React react);
}
