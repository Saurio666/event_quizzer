package com.sauriotech.eventquizzer.security;

import lombok.Getter;
import lombok.Setter;

/**
 * @author SAURIOTech Solutions.
 *
 */
public class Credentials {
	@Getter
	@Setter
	private Long quizId;
	@Getter
	@Setter
	private String userId;
	
	public Credentials(Long quizId, String userId) {
		super();
		this.quizId = quizId;
		this.userId = userId;
	}
		
}
