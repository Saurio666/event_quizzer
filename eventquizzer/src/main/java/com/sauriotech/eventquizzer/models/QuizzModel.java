package com.sauriotech.eventquizzer.models;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sauriotech.eventquizzer.utils.UIMessage;

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
	private String shortDescription;
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
	@JsonIgnore
	@Getter
	@Setter
	private String eventTimezone;
	@Getter
	@Setter
	private int quizzTime;
	
	@Getter
	@Setter
	private Long attemptId;
	
	@Getter
	@Setter
	private String quizUrl;
	
	@Getter
	@Setter
	private int attemptsPerUser;
	
	@JsonIgnore
	@Getter
	@Setter
	private Date quizzStartDate;
	
	@JsonIgnore
	@Getter
	@Setter
	private Date quizzEndDate;
	
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
	
	public QuizzModel(String status, UIMessage message) {
		super(status, message);
	}
}
