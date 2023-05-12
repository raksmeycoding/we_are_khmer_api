/**
 * 
 */
package com.kshrd.wearekhmer.emailVerification.model;

import lombok.*;

import java.util.List;

/**
 * @author mahes
 *
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class User {

	private String firstName;
	
	private String lastName;
	
	private String location;
	
	private List<TargetEmail> emails;
	
}
