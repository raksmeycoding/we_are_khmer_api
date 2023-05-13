/**
 *
 */
package com.kshrd.wearekhmer.controller;

import java.util.HashMap;
import java.util.Map;




import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kshrd.wearekhmer.model.User;
import com.kshrd.wearekhmer.service.EmailService;

/**
 * @author mahes
 *
 */
@RestController
@RequestMapping("/email")
public class EmailController {

	@Autowired
	private EmailService emailService;

	@PostMapping
	public ResponseEntity<Map<String, Object>> sendEmail(@RequestBody User user) throws MessagingException {
		Map<String, Object> map = new HashMap<>();
		map.put("message", emailService.sendEmail(user));
		return new ResponseEntity<>(map, HttpStatus.OK);
	}



}
