package com.kshrd.wearekhmer.opt.service;

import com.kshrd.wearekhmer.opt.model.Otp;
import com.kshrd.wearekhmer.opt.repository.OtpServiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;


@Service
@AllArgsConstructor
public class OtpServiceImpl implements OtpService{

    private final OtpServiceRepository otpServiceRepository;

    @Override
    public Otp createVerificationToken(String token, Timestamp expiredAt, String userId) {
        return otpServiceRepository.createVerificationToken(token, expiredAt, userId);
    }

    @Override
    public Otp findByToken(String token) {
        return otpServiceRepository.findByToken(token);
    }

    @Override
    public Otp removeByToken(String token) {
        return otpServiceRepository.removeByToken(token);
    }

    @Override
    public Otp enableUserByToken(String token) {
        return otpServiceRepository.enableUserByToken(token);
    }
}
