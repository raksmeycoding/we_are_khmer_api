package com.kshrd.wearekhmer.resetPassword.service;

import com.kshrd.wearekhmer.exception.DuplicateKeyException;
import com.kshrd.wearekhmer.exception.ValidateException;
import com.kshrd.wearekhmer.opt.model.Otp;
import com.kshrd.wearekhmer.resetPassword.model.entity.Reset;
import com.kshrd.wearekhmer.resetPassword.repository.ResetPasswordMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ResetPasswordImp implements ResetPassword{

    private final ResetPasswordMapper resetPasswordMapper;
    @Override
    public boolean checkEmail(String email) {
        if(!resetPasswordMapper.checkEmailExist(email))
            throw new ValidateException("Email does not exist",HttpStatus.NOT_FOUND,HttpStatus.NOT_FOUND.value());
        else
            return true;

    }

    @Override
    public boolean checkEmailExistInResetTb(String email) {
        if(!resetPasswordMapper.checkEmailExistInResetTb(email))
            return resetPasswordMapper.checkEmailExistInResetTb(email);
        else
            throw new DuplicateKeyException("Email already exist in OTP");
    }

    @Override
    public Reset createVerificationToken(Reset reset) {
        return resetPasswordMapper.createVerification(reset);
    }

    @Override
    public boolean VerifyOTP(String token) {
        if(resetPasswordMapper.verifyToken(token)){
            resetPasswordMapper.tokenVerifySuccess(token);
            return true;
        }else{
            throw new ValidateException("OTP is invalid ! ", HttpStatus.NOT_FOUND,HttpStatus.NOT_FOUND.value());
        }

    }

    @Override
    public Reset resetPassword(String email, String newPassword) {
        if(resetPasswordMapper.checkEmailAlreadyVerified(email)){
            resetPasswordMapper.resetPassword(email,newPassword);
            resetPasswordMapper.removeToken(email);
        }else{
            throw new ValidateException("email : "+email+ " does not verified", HttpStatus.FORBIDDEN, HttpStatus.FORBIDDEN.value());
        }
        return null;
    }
}
