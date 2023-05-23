package com.kshrd.wearekhmer.opt.service;

import com.kshrd.wearekhmer.opt.model.Otp;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;

public interface OtpService {

    Otp createVerificationToken(Otp otp);

    Otp findByToken(String token);

    Otp removeByToken(String token);

    Otp enableUserByToken(String token);

}
