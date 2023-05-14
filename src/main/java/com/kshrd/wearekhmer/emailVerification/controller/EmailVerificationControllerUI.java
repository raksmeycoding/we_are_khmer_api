package com.kshrd.wearekhmer.emailVerification.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class EmailVerificationControllerUI {


    @GetMapping ("/verify/email/token/{tokenId}")
    public String UserVerifyEmail() {
        return "user-verify-email";
    }
}
