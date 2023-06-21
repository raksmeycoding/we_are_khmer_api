package com.kshrd.wearekhmer.requestRequest;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenericResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    Integer totalRecords;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    Integer totalViewPerWeek;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Integer totalViewPerMonth;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Integer totalViewPerYear;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String type;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Integer totalComment;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String statusName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    Integer statusCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String title;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String instance;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    Object payload;



    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<String> details;


}
