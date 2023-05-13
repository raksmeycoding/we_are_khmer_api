/**
 * 
 */
package com.kshrd.wearekhmer.service;



import com.kshrd.wearekhmer.model.User;
import jakarta.mail.MessagingException;

/**
 * @author mahes
 *
 */
public interface EmailService {

	String sendEmail(User user) throws MessagingException;
}
