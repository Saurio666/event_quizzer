package com.sauriotech.eventquizzer.models;

import lombok.Getter;
import lombok.Setter;

/**
 * @author SAURIOTech Solutions.
 *
 */
public class QualifiedAnswerModel/* extends BaseModel */{
	@Getter
	@Setter
	private Long answerId;
	@Getter
	@Setter
	private boolean correct;
	@Getter
	@Setter
	private boolean selected;
	@Getter
	@Setter
	private Long questionId;
	@Getter
	@Setter
	private boolean shouldBeSelected;
	
	public QualifiedAnswerModel(Long answerId, boolean correct, boolean selected,
			Long questionId, boolean shouldBeSelected) {
		this.answerId = answerId;
		this.correct = correct;
		this.selected = selected;
		this.questionId = questionId;
		this.shouldBeSelected = shouldBeSelected;
	}
}
