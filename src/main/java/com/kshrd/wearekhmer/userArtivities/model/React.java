package com.kshrd.wearekhmer.userArtivities.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class React {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String reactId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String userId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String articleId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Timestamp createAt;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Timestamp updateAt;
    private boolean status;

//    1- get current user for each react
//    2- check if article is already,
    /*
    * 1- get current user id
    * 2 check article is already reacted or not
    *   2.1 if not insert into react_tb
    *   2.2 if react just update the status
    * */
}
