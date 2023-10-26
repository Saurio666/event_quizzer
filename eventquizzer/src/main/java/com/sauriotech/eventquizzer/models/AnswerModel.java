package com.sauriotech.eventquizzer.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

/**
 * @author SAURIOTech Solutions.
 *
 */
public class AnswerModel extends BaseModel {
	@Getter
	@Setter
	private Long id;
	@Getter
	@Setter
	private String answer;
	@JsonIgnore
	@Getter
	@Setter
	private Double weight;
	@Getter
	@Setter
	private Long questionId;
	
	public AnswerModel(String status, String message) {
		super(status, message);
	}
	
	public AnswerModel(Long id, String answer, Double weight, Long questionId) {
		this.id = id;
		this.answer = answer;
		this.weight = weight;
		this.questionId = questionId;
	}
}
