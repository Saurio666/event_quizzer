package com.sauriotech.eventquizzer.models;

import lombok.Getter;
import lombok.Setter;

/**
 * @author SAURIOTech Solutions.
 *
 */
public class EventAuthorizedUserModel extends BaseModel {
	@Getter
	@Setter
	private String userId;
	
	@Getter
	@Setter
	private String userName;
	
	@Getter
	@Setter
	private String userEmail;
	
	@Getter
	@Setter
	private String userCompanyName;

	@Getter
	@Setter
	private Long eventId;
	
	@Getter
	@Setter
	private String companyLocation;
	
	@Getter
	@Setter
	private String userPosition;
	
	@Getter
	@Setter
	private String userContactPhone;
	
	public EventAuthorizedUserModel(String status, String message) {
		super(status, message);
	}
}
