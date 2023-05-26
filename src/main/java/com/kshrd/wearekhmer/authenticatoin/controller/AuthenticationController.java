package com.kshrd.wearekhmer.authenticatoin.controller;


import com.kshrd.wearekhmer.authenticatoin.AuthenticationService;
import com.kshrd.wearekhmer.emailVerification.controller.TokenRequest;
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
import com.kshrd.wearekhmer.utils.OtpUtil;
import com.kshrd.wearekhmer.utils.WeAreKhmerCurrentUser;
import com.kshrd.wearekhmer.utils.serviceClassHelper.ServiceClassHelper;
import com.kshrd.wearekhmer.utils.userUtil.UserUtil;
import com.kshrd.wearekhmer.utils.validation.DefaultWeAreKhmerValidation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    private final DefaultWeAreKhmerValidation defaultWeAreKhmerValidation;


    @PostMapping("/register")
    public ResponseEntity<?> userRegister(@RequestBody @Validated NormalUserRequest normalUserRequest) {


        GenericResponse genericResponse = null;
        try {
//            gender validation
            defaultWeAreKhmerValidation.genderValidation(normalUserRequest.getGender());
//            password validation
            defaultWeAreKhmerValidation.passwordValidation(normalUserRequest.getPassword());

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
        AuthorRequestTable authorRequestTable = serviceClassHelper.insertAndGetAuthorRequestFromDatabase(authorRequest);
        GenericResponse AUTHOR_REQUEST_RESULT = GenericResponse.builder()
                .message("request successfully")
                .status("200")
                .payload(authorRequestTable)
                .build();
        return ResponseEntity.ok(AUTHOR_REQUEST_RESULT);
    }


    @PostMapping("/login")
    private ResponseEntity<?> userLogin(@RequestBody UserLoginRequest userLoginRequest) {
        GenericResponse genericResponse;
        try {
            return ResponseEntity.ok(authenticationService.authenticate(userLoginRequest));
        } catch (Exception ex) {
            if (ex instanceof DisabledException) {
                genericResponse = GenericResponse.builder()
                        .title("User not allowed.")
                        .status("400")
                        .message("You need to do code verification. please checkout your email and verify it. (" + ex.getMessage() + ")")
                        .build();
                return ResponseEntity.badRequest().body(genericResponse);
            }
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }







    @PostMapping("/verification/token")
    public ResponseEntity<?> emailVerificatoin(@RequestBody TokenRequest tokenRequest) {

        System.out.println(tokenRequest.getToken());
        GenericResponse genericResponse;
        try {
            log.info("sending email");
            Otp otp = otpService.enableUserByToken(tokenRequest.getToken());
            System.out.println(otp);

//            after verification, then login
            UserLoginRequest userLoginRequest =
                    UserLoginRequest.builder()
                            .email(otp.getEmail())
                            .password(otp.getTemp_password())
                            .build();
            ResponseEntity<?> response = authenticationService.authenticate(userLoginRequest);
            otpService.removeByToken(otp.getToken());
            return ResponseEntity.ok(response.getBody());
        } catch (Exception exception) {
            genericResponse = GenericResponse.builder()
                    .status("500")
                    .message(exception.getMessage())
                    .title("internal server error!")
                    .build();

            return ResponseEntity.internalServerError().body(genericResponse);


        }


    }
}
