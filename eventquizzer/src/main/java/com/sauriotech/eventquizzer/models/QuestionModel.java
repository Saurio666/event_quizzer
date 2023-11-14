package com.sauriotech.eventquizzer.models;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @author SAURIOTech Solutions.
 *
 */
public class QuestionModel extends BaseModel {
	@Getter
	@Setter
	private Long id;
	@Getter
	@Setter
	private String question;
	@Getter
	@Setter
	private String type;
	@Getter
	@Setter
	private boolean randomOptions;
	@Getter
	@Setter
	private List<AnswerModel> answers;
	@Getter
	@Setter
	private int correctAnswers;
	
	public QuestionModel(String status, String message) {
		super(status, message);
	}
	
		
	public QuestionModel(Long id, String question, String type, boolean randomOptions, List<AnswerModel> answers) {
		this.id = id;
		this.question = question;
		this.type = type;
		this.randomOptions = randomOptions;
		this.answers = answers;
	}
	
}
