package com.sauriotech.eventquizzer.models;

import lombok.Getter;
import lombok.Setter;

/**
 * @author SAURIOTech Solutions.
 *
 */
public class AnswerModel {
	@Getter
	@Setter
	private Long id;
	@Getter
	@Setter
	private String answer;
	@Getter
	@Setter
	private Double weight;
	@Getter
	@Setter
	private Long questionId;
	
	public AnswerModel(Long id, String answer, Double weight, Long questionId) {
		this.id = id;
		this.answer = answer;
		this.weight = weight;
		this.questionId = questionId;
	}
}
