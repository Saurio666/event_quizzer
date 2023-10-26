package com.sauriotech.eventquizzer.security;

import org.springframework.stereotype.Service;

import com.sauriotech.eventquizzer.utils.DummyLogger;

/**
 * @author SAURIOTech Solutions.
 *
 */
@Service
public class BasicAuthorizer {
	private static final String KEY = "mpieqwcqafrxbkpvakumwcqlfrfqph";
	
	public Credentials parseToken(String token) {
		DummyLogger.sysout("BasicAuthorizer", "parseToken", "token: " + token);
		String data = Encrypter.decrypt(token, KEY);
		
		if(data != null) {
			String[] info = data.split(";");
			DummyLogger.sysout("BasicAuthorizer", "parseToken", "info: " + info);
			
			if(info.length == 2) {
				DummyLogger.sysout("BasicAuthorizer", "parseToken", "info[0] " + info[0]);
				DummyLogger.sysout("BasicAuthorizer", "parseToken", "info[1] " + info[1]);
				
				return new Credentials(Long.parseLong(info[0]), info[1]);
			}			
			else if(info.length == 1) {
				DummyLogger.sysout("BasicAuthorizer", "parseToken", "info[0] " + info[0]);
				
				return new Credentials(Long.parseLong(info[0]), null);
			}
		}
		
		return null;
	}
}
