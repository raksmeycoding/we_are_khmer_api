package com.kshrd.wearekhmer.emailVerification.controller;


import com.kshrd.wearekhmer.emailVerification.service.EmailService;
import com.kshrd.wearekhmer.opt.model.Otp;
import com.kshrd.wearekhmer.opt.service.OtpService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/v1/email")
public class EmailVerificationController {

    private final EmailService emailService;
    private final OtpService otpService;


    @PostMapping("/verification/token/{tokenId}")
    public ResponseEntity<?> emailVerificatoin(@RequestBody TokenRequest tokenRequest) {

        System.out.println(tokenRequest.getToken());

        final AtomicInteger counter = new AtomicInteger(0);
        try {
            log.info("sending email");
//          enable user
            Otp otp = otpService.enableUserByToken(tokenRequest.getToken());

            System.out.println("found token: " + otp);
            ResponseEntity.ok("user enablee success");

        } catch (Exception exception) {
            log.error("sending email error");
            System.out.println(exception);
            counter.incrementAndGet();
        }

        if (counter.get() > 0) {
            return ResponseEntity.badRequest().body("sending email get error.");
        }
        return ResponseEntity.ok("sending email successfully");
    }

}
