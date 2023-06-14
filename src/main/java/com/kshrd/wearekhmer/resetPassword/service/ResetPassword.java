package com.kshrd.wearekhmer.resetPassword.service;

import com.kshrd.wearekhmer.opt.model.Otp;
import com.kshrd.wearekhmer.resetPassword.model.entity.Reset;

public interface ResetPassword {
    boolean checkEmail(String email);

    boolean checkEmailExistInResetTb(String email);

    Reset createVerificationToken(Reset reset);

    boolean VerifyOTP(String token);

    Reset resetPassword(String email, String newPassword);
}
