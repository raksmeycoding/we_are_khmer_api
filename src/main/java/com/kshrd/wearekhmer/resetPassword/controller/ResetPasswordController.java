package com.kshrd.wearekhmer.resetPassword.controller;


import com.kshrd.wearekhmer.authenticatoin.AuthenticationService;
import com.kshrd.wearekhmer.emailVerification.service.EmailService;
import com.kshrd.wearekhmer.exception.DuplicateKeyException;
import com.kshrd.wearekhmer.opt.model.Otp;
import com.kshrd.wearekhmer.opt.service.OtpService;
import com.kshrd.wearekhmer.requestRequest.GenericResponse;
import com.kshrd.wearekhmer.requestRequest.NormalUserRequest;
import com.kshrd.wearekhmer.resetPassword.model.entity.Reset;
import com.kshrd.wearekhmer.resetPassword.model.request.InputEmail;
import com.kshrd.wearekhmer.resetPassword.model.request.NewPassword;
import com.kshrd.wearekhmer.resetPassword.repository.ResetPasswordMapper;
import com.kshrd.wearekhmer.resetPassword.service.ResetPasswordImp;
import com.kshrd.wearekhmer.user.model.entity.UserApp;
import com.kshrd.wearekhmer.user.service.userService.UserAppDetailsServiceImpl;
import com.kshrd.wearekhmer.utils.OtpUtil;
import com.kshrd.wearekhmer.utils.userUtil.UserUtil;
import com.kshrd.wearekhmer.utils.validation.DefaultWeAreKhmerValidation;
import com.kshrd.wearekhmer.utils.validation.WeAreKhmerValidation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/reset")
@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
public class ResetPasswordController {
    private final OtpService otpService;
    private final OtpUtil otpUtil;
    private final UserAppDetailsServiceImpl userAppDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;

    private final UserUtil userUtil;
    private final ResetPasswordMapper resetPasswordMapper;


    private final AuthenticationService authenticationService;

    private final ResetPasswordImp resetPasswordImp;

    private final EmailService emailService;

    private final WeAreKhmerValidation weAreKhmerValidation;

    private final DefaultWeAreKhmerValidation defaultWeAreKhmerValidation;



    @PostMapping("/email")
    @Operation(summary = "Input your email")
    public ResponseEntity<?> inputEmailToReset(@RequestBody InputEmail email) {

        GenericResponse genericResponse = null;

        weAreKhmerValidation.validateEmail(email.getEmail());

        resetPasswordImp.checkEmailExistInResetTb(email.getEmail());


        resetPasswordImp.checkEmail(email.getEmail());


        try {
            InputEmail send = InputEmail.builder()
                    .email(email.getEmail())
                    .build();

            Reset reset = Reset.builder()
                    .token(otpUtil.getGeneratedUUid())
                    .email(email.getEmail())
                    .build();

            Reset insertToTb = resetPasswordImp.createVerificationToken(reset);

            System.out.println(insertToTb);

            emailService.sendResetVerification(send.getEmail(), insertToTb.getToken());


            genericResponse = GenericResponse.builder()
                    .title("request to reset was succeed!")
                    .message("get verification from your email and verify.")
                    .status(String.valueOf(HttpStatus.OK.value()))
                    .build();
            return ResponseEntity.ok(genericResponse);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/verify")
    @Operation(summary = "verify otp to reset password")
    public ResponseEntity<?> verifyOTP(@RequestParam("otp") String otp){

        boolean verifyOtp = resetPasswordImp.VerifyOTP(otp);

        GenericResponse genericResponse = GenericResponse.builder()
                .title("success")
                .statusCode(200)
                .message("You have already verify OTP")
                .build();

        return ResponseEntity.ok(genericResponse);
    }

    @PutMapping("/reset")
    @Operation(summary = "reset password")
    public ResponseEntity<?> resetPassword(
            @RequestParam("email") String email,
            @RequestBody NewPassword newPassword
    ){
        defaultWeAreKhmerValidation.passwordValidation(newPassword.getNewPassword());

        String rawPassword = passwordEncoder.encode(newPassword.getNewPassword());

        Reset reset = resetPasswordImp.resetPassword(email, rawPassword);

        GenericResponse genericResponse = GenericResponse.builder()
                .title("success")
                .message("You have successfully reset password")
                .statusCode(200)
                .build();

        return ResponseEntity.ok(genericResponse);
    }

}
