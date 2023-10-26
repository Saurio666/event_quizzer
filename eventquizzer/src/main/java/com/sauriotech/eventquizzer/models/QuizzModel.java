package com.sauriotech.eventquizzer.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

/**
 * @author SAURIOTech Solutions.
 *
 */
public class QuizzModel extends BaseModel {
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
	private List<QuestionModel> questions;
	@Getter
	@Setter
	private boolean showScoreOnFinish;
	@Getter
	@Setter
	private String imageUrl;
	@JsonIgnore
	@Getter
	@Setter
	private boolean requiresAuthorization;
	
	public QuizzModel(Long id, String title, String description, List<QuestionModel> questions,
			String status, String message) {
		super(status, message);
		this.id = id;
		this.title = title;
		this.description = description;
		this.questions = questions;
	}
	
	public QuizzModel(String status, String message) {
		super(status, message);
	}
}
