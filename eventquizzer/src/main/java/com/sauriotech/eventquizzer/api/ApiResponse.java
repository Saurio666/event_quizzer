package com.sauriotech.eventquizzer.api;

import java.util.Map;

import com.sauriotech.eventquizzer.utils.UIMessage;

import lombok.Getter;
import lombok.Setter;

/**
 * @author SAURIOTech Solutions.
 *
 */
public class ApiResponse {
	@Getter	
	@Setter
	private String apiResponseStatus;
	@Getter	
	@Setter
	private String apiResponseMessage;
	@Getter	
	@Setter
	private Map<String, Object> data;
	
	@Getter
	@Setter
	private UIMessage uiMessage;
	
	public ApiResponse(String apiResponseStatus, String apiResponseMessage) {
		this.apiResponseStatus = apiResponseStatus;
		this.apiResponseMessage = apiResponseMessage;
	}

	public ApiResponse(String apiResponseStatus, UIMessage uiMessage) {
		this.apiResponseStatus = apiResponseStatus;
		this.uiMessage = uiMessage;
	}
	
	public ApiResponse(String apiResponseStatus, String apiResponseMessage,
			Map<String, Object> data) {
		this.apiResponseStatus = apiResponseStatus;
		this.apiResponseMessage = apiResponseMessage;
		this.data = data;
	}
}
