package com.kshrd.wearekhmer.userReport.request.userReportAuthor;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class UserReportAuthorRequestBody {

    @NotNull(message = "reason must be not null.")
    @NotBlank(message = "reason must not be blank.")
    private String reason;
}
