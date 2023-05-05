package com.kshrd.wearekhmer.files.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileResponse<T> {
    private String status;
    private String message;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T payload;


}
