package com.sauriotech.eventquizzer.models;

import com.sauriotech.eventquizzer.utils.UIMessage;

import lombok.Getter;
import lombok.Setter;

/**
 * @author SAURIOTech Solutions.
 *
 */
public class BaseModel {
	@Getter
	@Setter
	private String status;
	@Getter
	@Setter
	private String message;
	@Getter
	@Setter
	private UIMessage uiMessage;

	public BaseModel() {
	}

	public BaseModel(String status, String message) {
		this.status = status;
		this.message = message;
	}
	
	public BaseModel(String status, UIMessage uiMessage) {
		this.status = status;
		this.uiMessage = uiMessage;
	}

}
