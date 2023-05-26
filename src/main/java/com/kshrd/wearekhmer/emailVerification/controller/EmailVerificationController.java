package com.kshrd.wearekhmer.emailVerification.controller;


import com.kshrd.wearekhmer.authenticatoin.AuthenticationService;
import com.kshrd.wearekhmer.emailVerification.service.EmailService;
import com.kshrd.wearekhmer.opt.model.Otp;
import com.kshrd.wearekhmer.opt.service.OtpService;
import com.kshrd.wearekhmer.requestRequest.GenericResponse;
import com.kshrd.wearekhmer.requestRequest.UserLoginRequest;
import com.kshrd.wearekhmer.requestRequest.UserRepsonse;
import com.kshrd.wearekhmer.utils.InMemoryTempoUserPassword;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/v1/email")
@Hidden
public class EmailVerificationController {

    private final EmailService emailService;
    private final OtpService otpService;

    private final AuthenticationService authenticationService;

    private final InMemoryTempoUserPassword inMemoryTempoUserPassword;


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
