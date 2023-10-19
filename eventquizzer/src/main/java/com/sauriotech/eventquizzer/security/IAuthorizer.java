package com.sauriotech.eventquizzer.security;

/**
 * @author SAURIOTech Solutions.
 *
 */
public interface IAuthorizer {
	public boolean validateToken(String token);
}
