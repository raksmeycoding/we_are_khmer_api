package com.kshrd.wearekhmer.authenticatoin.controller;


import com.kshrd.wearekhmer.authenticatoin.AuthenticationService;
import com.kshrd.wearekhmer.emailVerification.service.EmailService;
import com.kshrd.wearekhmer.exception.DuplicateKeyException;
import com.kshrd.wearekhmer.opt.model.Otp;
import com.kshrd.wearekhmer.opt.service.OtpService;
import com.kshrd.wearekhmer.requestRequest.GenericResponse;
import com.kshrd.wearekhmer.requestRequest.NormalUserRequest;
import com.kshrd.wearekhmer.requestRequest.UserLoginRequest;
import com.kshrd.wearekhmer.user.model.entity.AuthorRequest;
import com.kshrd.wearekhmer.user.model.entity.AuthorRequestTable;
import com.kshrd.wearekhmer.user.model.entity.UserApp;
import com.kshrd.wearekhmer.user.repository.AuthorRepository;
import com.kshrd.wearekhmer.user.repository.EducationMapper;
import com.kshrd.wearekhmer.user.repository.QuoteMapper;
import com.kshrd.wearekhmer.user.repository.WorkingExperienceMapper;
import com.kshrd.wearekhmer.user.service.userService.UserAppDetailsServiceImpl;
import com.kshrd.wearekhmer.utils.InMemoryTempoUserPassword;
import com.kshrd.wearekhmer.utils.OtpUtil;
import com.kshrd.wearekhmer.utils.UserTemPassword;
import com.kshrd.wearekhmer.utils.WeAreKhmerCurrentUser;
import com.kshrd.wearekhmer.utils.serviceClassHelper.ServiceClassHelper;
import com.kshrd.wearekhmer.utils.userUtil.UserUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
@Slf4j
public class AuthenticationController {
    private final UserAppDetailsServiceImpl userAppDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;

    private final UserUtil userUtil;

    private final AuthenticationService authenticationService;

    private final EmailService emailService;

    private final OtpService otpService;
    private final OtpUtil otpUtil;


    private final WeAreKhmerCurrentUser weAreKhmerCurrentUser;

    private final EducationMapper educationMapper;

    private final QuoteMapper quoteMapper;

    private final WorkingExperienceMapper workingExperienceMapper;


    private final AuthorRepository authorRepository;


    private final ServiceClassHelper serviceClassHelper;


    @PostMapping("/register")
    public ResponseEntity<?> userRegister(@RequestBody NormalUserRequest normalUserRequest) {


        GenericResponse genericResponse = null;
        try {
            NormalUserRequest n = NormalUserRequest.builder()
                    .email(normalUserRequest.getEmail())
                    .password(passwordEncoder.encode(normalUserRequest.getPassword()))
                    .gender(normalUserRequest.getGender())
                    .build();
            UserApp n2 = userAppDetailsService.normalUserRegister(n);
            //            sending email verification
            log.info("""
                        Sending Email...
                        """);
            Otp otpGen = Otp.builder()
                    .token(otpUtil.getGeneratedUUid())
                    .email(normalUserRequest.getEmail())
                    .temp_password(normalUserRequest.getPassword())
                    .expiredAt(otpUtil.getExpiredAt())
                    .userId(n2.getUserId())
                    .build();
            Otp otp = otpService.createVerificationToken(otpGen);
            System.out.println(otp);

            emailService.sendVerificationEmail(n2.getEmail(), otp.getToken());
            log.info("""
                        Finish Sending Email...
                        """);

            genericResponse = GenericResponse.builder()
                    .title("register succeed!")
                    .message("get verification from your email and verify.")
                    .status(String.valueOf(HttpStatus.OK.value()))
                    .build();
            return ResponseEntity.ok(genericResponse);
        } catch (DataIntegrityViolationException | MessagingException ex) {
            if (ex instanceof DataIntegrityViolationException) {
                throw new DuplicateKeyException("Email is already login.");
            }

            throw new RuntimeException();
        }
    }


    @PostMapping("/register/as-author")
    public ResponseEntity<?> registerAsAuthor(@RequestBody AuthorRequest authorRequest) {
        try {
            AuthorRequestTable authorRequestTable = serviceClassHelper.insertAndGetAuthorRequestFromDatabase(authorRequest);
            GenericResponse AUTHOR_REQUEST_RESULT = GenericResponse.builder()
                    .message("request successfully")
                    .status("200")
                    .payload(authorRequestTable)
                    .build();
            return ResponseEntity.ok(AUTHOR_REQUEST_RESULT);
        } catch (Exception ex) {
            System.out.println(ex);
            return ResponseEntity.ok(ex.getMessage());
        }
    }


    @PostMapping("/login")
    private ResponseEntity<?> userLogin(@RequestBody UserLoginRequest userLoginRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(userLoginRequest));
    }
}
