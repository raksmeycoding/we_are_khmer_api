package com.kshrd.wearekhmer.userReport.request.userReportAuthor;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AdminIsApproveRequestBody {


    @NotNull(message = "status must be not null.")
    @NotBlank(message = "status must not be blank.")
    private String status;
}
