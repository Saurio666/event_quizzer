package com.sauriotech.eventquizzer.models;

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

	public BaseModel() {
	}

	public BaseModel(String status, String message) {
		this.status = status;
		this.message = message;
	}

}
