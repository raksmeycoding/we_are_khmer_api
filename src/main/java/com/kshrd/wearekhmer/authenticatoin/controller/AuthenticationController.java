package com.kshrd.wearekhmer.authenticatoin.controller;


import com.kshrd.wearekhmer.authenticatoin.AuthenticationService;
import com.kshrd.wearekhmer.emailVerification.service.EmailService;
import com.kshrd.wearekhmer.opt.model.Otp;
import com.kshrd.wearekhmer.opt.service.OtpService;
import com.kshrd.wearekhmer.user.model.dto.UserAppDTO;
import com.kshrd.wearekhmer.user.model.entity.AuthorRequest;
import com.kshrd.wearekhmer.user.model.entity.UserApp;
import com.kshrd.wearekhmer.request.NormalUserRequest;
import com.kshrd.wearekhmer.request.UserLoginRequest;
import com.kshrd.wearekhmer.user.service.UserAppDetailsServiceImpl;
import com.kshrd.wearekhmer.utils.OtpUtil;
import com.kshrd.wearekhmer.utils.userUtil.UserUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("/register")
    public ResponseEntity<UserAppDTO> userRegister(@RequestBody NormalUserRequest normalUserRequest ) {
        try {
            NormalUserRequest n = NormalUserRequest.builder()
                    .email(normalUserRequest.getEmail())
                    .password(passwordEncoder.encode(normalUserRequest.getPassword()))
                    .gender(normalUserRequest.getGender())
                    .build();
            UserApp n2 = userAppDetailsService.normalUserRegister(n);
            if(n2 != null) {
                //            sending email verification
                log.info("""
                    Sending Email...
                    """);
                Otp otp = otpService.createVerificationToken(otpUtil.getGeneratedUUid(), otpUtil.getExpiredAt(), n2.getUserId());
                System.out.println(otp);

                emailService.sendVerificationEmail(n2.getEmail(), otp.getToken());
                log.info("""
                    Finish Sending Email...
                    """);
            }
            UserAppDTO userAppDTO = userUtil.toUserAppDTO(n2);
            return ResponseEntity.ok(userAppDTO);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex.getClass());
            throw new RuntimeException();
        }
    };

    @PostMapping("/register/as-author/user/{userId}")
    public ResponseEntity<?> registerAsAuthor(@RequestBody AuthorRequest authorRequest) {
        try {

            System.out.println(authorRequest);

//            UserApp userApp = userAppDetailsService.registerAsAuthorAndReturnUserApp(userId);
//            UserAppDTO userAppDTO = userUtil.toUserAppDTO(userApp);
            return ResponseEntity.ok(authorRequest);
        } catch (Exception ex) {
            System.out.println(ex);
            return ResponseEntity.ok(ex.getMessage());
        }
    }



    @PostMapping("/login")
    private ResponseEntity<?> userLogin(@RequestBody UserLoginRequest userLoginRequest){
        return ResponseEntity.ok(authenticationService.authenticate(userLoginRequest));
    }
}
