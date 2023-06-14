package com.kshrd.wearekhmer.emailVerification.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;


@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService{


    private final SpringTemplateEngine springTemplateEngine;

    private final JavaMailSenderImpl mailSender;




    @Override
    public void sendVerificationEmail(String to, String tokenId) throws MessagingException {
        SimpleMailMessage message = new SimpleMailMessage();
        Context context = new Context();
        Map<String, Object> map = new HashMap<>();
        map.put("link", "http://localhost:3000/codeVerification");
        map.put("code", tokenId);
        context.setVariables(map);
        String process = springTemplateEngine.process("email-verification", context);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setText(process, true);
        mimeMessageHelper.setSubject("Invitation to domrra");
        mimeMessageHelper.setFrom("no-reply@ethandev.com");
        mailSender.send(mimeMessage);

    }


    @Override
    public void sendResetVerification(String to, String tokenId) throws MessagingException {
        SimpleMailMessage message = new SimpleMailMessage();
        Context context = new Context();
        Map<String, Object> map = new HashMap<>();
        map.put("link", "http://localhost:3000/resetVerification");
        map.put("code", tokenId);
        context.setVariables(map);
        String process = springTemplateEngine.process("reset-verification", context);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setText(process, true);
        mimeMessageHelper.setSubject("Verification to reset password");
        mimeMessageHelper.setFrom("no-reply@ethandev.com");
        mailSender.send(mimeMessage);
    }
}
