package com.kshrd.wearekhmer.request;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenericResponse {
    String status;
    String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    Object payload;
}
