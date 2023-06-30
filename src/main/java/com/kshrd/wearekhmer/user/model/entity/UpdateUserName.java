package com.kshrd.wearekhmer.user.model.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserName {

    @NotNull(message = "username can not be null.")
    @NotBlank(message = "username can not be blank.")
    private String userName;
}
