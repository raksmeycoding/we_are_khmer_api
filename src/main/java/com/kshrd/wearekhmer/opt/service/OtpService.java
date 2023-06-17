package com.kshrd.wearekhmer.opt.service;

import com.kshrd.wearekhmer.opt.model.Otp;
import com.kshrd.wearekhmer.opt.model.Otp2;
import com.kshrd.wearekhmer.user.model.entity.UserApp;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;

public interface OtpService {

    Otp createVerificationToken(Otp otp);

    Otp findByToken(String token);

    Otp removeByToken(String token);

    Otp enableUserByToken(String token);

    Otp2 resendVerificationTokenToVerifyEmail(String email);

}
