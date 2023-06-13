package com.kshrd.wearekhmer.resetPassword.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Response {
    Integer statusCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String title;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String message;
}
