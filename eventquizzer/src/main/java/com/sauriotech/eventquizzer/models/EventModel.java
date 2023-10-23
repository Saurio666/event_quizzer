package com.sauriotech.eventquizzer.models;

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
}
