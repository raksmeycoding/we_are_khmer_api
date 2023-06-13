package com.kshrd.wearekhmer.authenticatoin.controller;


import com.kshrd.wearekhmer.authenticatoin.AuthenticationService;
import com.kshrd.wearekhmer.emailVerification.controller.TokenRequest;
import com.kshrd.wearekhmer.emailVerification.service.EmailService;
import com.kshrd.wearekhmer.exception.DuplicateKeyException;
import com.kshrd.wearekhmer.exception.ValidateException;
import com.kshrd.wearekhmer.opt.model.Otp;
import com.kshrd.wearekhmer.opt.service.OtpService;
import com.kshrd.wearekhmer.repository.WeAreKhmerRepositorySupport;
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
import com.kshrd.wearekhmer.utils.validation.WeAreKhmerValidation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
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

    private final WeAreKhmerRepositorySupport weAreKhmerRepositorySupport;

    private final WeAreKhmerValidation weAreKhmerValidation;


    @PostMapping("/register")
    @Operation(summary = "(User can register our application here.)")
    public ResponseEntity<?> userRegister(@RequestBody @Validated NormalUserRequest normalUserRequest) {


        GenericResponse genericResponse = null;
        try {
//            validate email
            defaultWeAreKhmerValidation.validateEmail(normalUserRequest.getEmail());
//            gender validation
            defaultWeAreKhmerValidation.genderValidation(normalUserRequest.getGender());
//            password validation
            defaultWeAreKhmerValidation.passwordValidation(normalUserRequest.getPassword());

            NormalUserRequest n = NormalUserRequest.builder()
                    .email(normalUserRequest.getEmail())
                    .password(passwordEncoder.encode(normalUserRequest.getPassword()))
                    .gender(normalUserRequest.getGender().toLowerCase())
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
                    .title("success")
                    .message("Register successfully, please get verification code from your email and verify.")
                    .statusCode(200)
                    .build();
            return ResponseEntity.ok(genericResponse);
        } catch (DataIntegrityViolationException | MessagingException ex) {
            if (ex instanceof DataIntegrityViolationException) {
                throw new ValidateException("This email is already registered!", HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value());
            }
            throw new RuntimeException(((MessagingException) ex).getCause());
        }
    }


    @PostMapping("/register/as-author")
    @Operation(summary = "(This controller works both either post or update)")
    public ResponseEntity<?> registerAsAuthor(@RequestBody AuthorRequest authorRequest) {
        java.sql.Date dateOfBirth = defaultWeAreKhmerValidation.validateDateOfBirth(authorRequest.getDateOfBirth());
        if (authorRequest.getWorkingExperience().size() > 3) {
            throw new ValidateException("Working Experience has 3 maximum fields", HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value());
        }
        if (authorRequest.getEducation().size() > 3) {
            throw new ValidateException("Education has 3 maximum fields", HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value());
        }
        if (authorRequest.getQuote().size() > 3) {
            throw new ValidateException("Quote has 3 maximum fields", HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value());
        }

        AuthorRequestTable authorRequestTable = serviceClassHelper.insertAndGetAuthorRequestFromDatabase(authorRequest);
        if (authorRequestTable != null) {
            weAreKhmerRepositorySupport.changeGenderAfterUserRequestAsAuthorSuccess(authorRequest.getGender(), authorRequest.getAuthorName(), authorRequestTable.getAuthorRequestId());
            weAreKhmerRepositorySupport.updateDateOfBirthOfUserAfterRegisteredAsAuthor(authorRequestTable.getUserId(), new java.sql.Timestamp(dateOfBirth.getTime()));
        }
        GenericResponse AUTHOR_REQUEST_RESULT = GenericResponse.builder()
                .message("You request as author successfully. ⚠️ Import noted: For this controller either works both for post and update.")
                .title("success")
                .statusCode(200)
                .payload(authorRequestTable)
                .build();
        return ResponseEntity.ok(AUTHOR_REQUEST_RESULT);

    }


    @PostMapping("/login")
    @Operation(summary = "(Dommra user can register here)")
    private ResponseEntity<?> userLogin(@RequestBody UserLoginRequest userLoginRequest) {
        System.out.println(userLoginRequest);
        defaultWeAreKhmerValidation.validateEmail(userLoginRequest.getEmail());
        defaultWeAreKhmerValidation.passwordValidation(userLoginRequest.getPassword());
        GenericResponse genericResponse;
        try {
            return ResponseEntity.ok(authenticationService.authenticate(userLoginRequest));
        } catch (Exception ex) {
            if (ex instanceof DisabledException) {
                genericResponse = GenericResponse.builder()
                        .title("error")
                        .statusCode(HttpStatus.UNAUTHORIZED.value())
                        .message("You need to do code verification. please checkout your email and verify it. (" + ex.getMessage() + ")")
                        .build();
                return ResponseEntity.badRequest().body(genericResponse);
            }
            if (ex instanceof BadCredentialsException) {
                throw new BadCredentialsException(null, new ValidateException("Invalid user name or password.", HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.value()));
            }
            throw new RuntimeException(ex.getCause());
        }
    }


    @PostMapping("/verification/token")
    @Operation(summary = "(Domrra user can verify email verification token here)")
    public ResponseEntity<?> emailVerificatoin(@RequestBody TokenRequest tokenRequest) {

        Otp findOtp = otpService.findByToken(tokenRequest.getToken());
        if(findOtp == null) {
            throw new ValidateException("No verification token had been found!", HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value());
        }
        GenericResponse genericResponse;
        try {
            Otp otp = otpService.enableUserByToken(tokenRequest.getToken());

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
            throw new RuntimeException(exception.getCause());
        }


    }
}
