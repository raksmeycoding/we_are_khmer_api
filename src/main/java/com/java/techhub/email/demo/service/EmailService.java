/**
 * 
 */
package com.java.techhub.email.demo.service;



import com.java.techhub.email.demo.model.User;
import jakarta.mail.MessagingException;

/**
 * @author mahes
 *
 */
public interface EmailService {

	String sendEmail(User user) throws MessagingException;
}
