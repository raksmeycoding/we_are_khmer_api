/**
 * 
 */
package com.kshrd.wearekhmer.emailVerification.service;

import com.kshrd.wearekhmer.emailVerification.model.User;
import jakarta.mail.MessagingException;

/**
 * @author mahes
 *
 */
public interface EmailService {

	String sendEmail(User user) throws MessagingException;
}
