/**
 * 
 */
package com.kshrd.wearekhmer.emailVerification.model;

import lombok.*;

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
public class TargetEmail {

	private String firstName;
	
	private String lastName;
	
	private String email;
	
}
