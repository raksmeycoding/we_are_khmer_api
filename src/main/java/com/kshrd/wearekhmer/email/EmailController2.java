package com.kshrd.wearekhmer.email;

import com.kshrd.wearekhmer.model.User;
import com.kshrd.wearekhmer.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/email")
public class EmailController2 {

    @Autowired
    private EmailService emailService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> sendEmail(@RequestBody User user) throws MessagingException {
        Map<String, Object> map = new HashMap<>();
        map.put("message", emailService.sendEmail(user));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
