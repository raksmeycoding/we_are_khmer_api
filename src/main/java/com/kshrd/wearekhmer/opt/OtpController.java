package com.kshrd.wearekhmer.opt;


import com.kshrd.wearekhmer.emailVerification.service.EmailService;
import com.kshrd.wearekhmer.exception.ValidateException;
import com.kshrd.wearekhmer.opt.model.Otp2;
import com.kshrd.wearekhmer.opt.repository.OtpServiceRepository;
import com.kshrd.wearekhmer.opt.request.ResendEmailVerificationTokenRequest;
import com.kshrd.wearekhmer.opt.service.OtpService;
import com.kshrd.wearekhmer.requestRequest.GenericResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RequestMapping("/api/v1/token")
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class OtpController {

    private final OtpService otpService;
    private final OtpServiceRepository otpServiceRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private final EmailService emailService;

    @PostMapping("/resendEmailVerificationToken")
    public ResponseEntity<?> resendEmailVerification(@RequestBody @Validated ResendEmailVerificationTokenRequest request) {
        try {
            // ⚠️ this logic, our database total handle it;
//            System.out.println(otpServiceRepository.getUserRawPasswordByUserEmail(request.getEmail()));
//            boolean isInputCorrectPassword = passwordEncoder.matches(request.getPassword(), otpServiceRepository.getUserRawPasswordByUserEmail(request.getEmail()));
//            System.out.println(isInputCorrectPassword);
//            if (!isInputCorrectPassword) {
//                throw new ValidateException("Input valid password.", HttpStatus.FORBIDDEN, HttpStatus.FORBIDDEN.value());
//            }
            Otp2 getNewGeneratedToken = otpService.resendVerificationTokenToVerifyEmail(request.getEmail());
            emailService.sendResendVerificationCode(getNewGeneratedToken.getEmail(), getNewGeneratedToken.getToken());
            return ResponseEntity.ok().body(GenericResponse.builder()
                    .title("success")
                    .statusCode(200)
                    .message("Token resending have been finished. Please check your email and verify your email address.")
                    .build());
        } catch (Exception ex) {
            if (ex.getCause() instanceof SQLException) {
                String exceptionMessage = ex.getCause().getMessage();
                String errorMessage = exceptionMessage.split(":", 2)[1];
                errorMessage = exceptionMessage.split("\n", 2)[0];
                throw new ValidateException(errorMessage, HttpStatus.FORBIDDEN, HttpStatus.FORBIDDEN.value());
            }
            throw new RuntimeException(ex.getCause());
        }
    }
}
