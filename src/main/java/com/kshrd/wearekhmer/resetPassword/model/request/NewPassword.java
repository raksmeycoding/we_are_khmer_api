package com.kshrd.wearekhmer.resetPassword.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewPassword {

    @NotNull(message = "email must be not null")
    @NotEmpty(message = "email must be not empty")
    private String email;
    @NotNull(message = "newPassword must be not null")
    @NotEmpty(message = "newPassword must be not empty")
    private String newPassword;
}
