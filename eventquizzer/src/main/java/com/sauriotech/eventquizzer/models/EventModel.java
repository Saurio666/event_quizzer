package com.sauriotech.eventquizzer.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sauriotech.eventquizzer.utils.UIMessage;

import lombok.Getter;
import lombok.Setter;

/**
 * @author SAURIOTech Solutions.
 *
 */
public class EventModel extends BaseModel {
	@Getter
	@Setter
	private Long id;
	@Getter
	@Setter
	private String title;
	@Getter
	@Setter
	private String description;
	@Getter
	@Setter
	private String imageUrl;
	@JsonIgnore
	@Getter
	@Setter
	private boolean requiresAuthorization;
	@Getter
	@Setter
	private String eventTimezone;
	@Getter
	@Setter
	private String extraContent;
	
	@Getter
	@Setter
	private Date eventStartDate;
	
	@Getter
	@Setter
	private Date eventEndDate;
	
	public EventModel() {}
	
	public EventModel(Long id, String title, String description) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
	}
	
	public EventModel(Long id, String title, String description,
			String status, String message) {
		super(status, message);
		this.id = id;
		this.title = title;
		this.description = description;
	}
	
	public EventModel(String status, String message) {
		super(status, message);
	}
	
	public EventModel(String status, UIMessage message) {
		super(status, message);
	}
}
