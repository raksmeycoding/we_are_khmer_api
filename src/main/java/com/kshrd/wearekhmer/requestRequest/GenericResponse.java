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
    String status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String title;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    Object payload;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<String> details;
}
