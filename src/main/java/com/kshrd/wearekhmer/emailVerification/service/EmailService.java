package com.kshrd.wearekhmer.emailVerification.service;

import jakarta.mail.MessagingException;

public interface EmailService {

    public void sendVerificationEmail(String to, String tokenId) throws MessagingException;

    public void sendResetVerification(String to, String tokenId) throws MessagingException;

    public void sendResendVerificationCode(String to, String token) throws MessagingException;

    public void sendEmailToAuthor(String to, String name) throws MessagingException;

    public void rejectEmailToAuthor(String to, String name) throws MessagingException;

    public void banAnnouncementToAuthor(String to, String name) throws MessagingException;

    public void unBanAnnouncementToAuthor(String to, String name) throws MessagingException;
}
