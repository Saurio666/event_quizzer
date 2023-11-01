package com.sauriotech.eventquizzer.utils;

import lombok.Getter;
import lombok.Setter;

/**
 * @author SAURIOTech Solutions.
 *
 */
public class UIMessage {
	public static final String TYPE_ERROR = "ERR";
	public static final String TYPE_WARNING = "WARN";
	public static final String TYPE_INFO= "INFO";
	
	@Getter
	@Setter
	private String key;
	@Getter
	@Setter
	private String type;
	@Getter
	@Setter
	private String message;

	public UIMessage() {
	}

	public UIMessage(String type, String message) {
		this.type = type;
		this.message = message;
	}

	public UIMessage(String key, String type, String message) {
		this.key = key;
		this.type = type;
		this.message = message;
	}
}
