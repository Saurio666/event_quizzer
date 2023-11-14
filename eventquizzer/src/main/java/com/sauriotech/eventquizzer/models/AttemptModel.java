package com.sauriotech.eventquizzer.models;

import java.util.Date;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * @author SAURIOTech Solutions.
 *
 */
public class AttemptModel extends BaseModel {
	@Getter
	@Setter
	public Long id;
	
	@Getter
	@Setter	
	private String userId;
	
	@Getter
	@Setter
	private Long quizId;
	
	@Getter
	@Setter
	private Double attemptScore;

	@Getter
	@Setter
	private Date attemptStart;
	
	@Getter
	@Setter
	private Date attemptEnd;
	
	@Getter
	@Setter
	private String attemptStatus;

	@Getter
	@Setter
	private Map<Long, QualifiedAnswerModel> qualifiedData;
	@Getter
	@Setter
	private Double attemptPercentage;
}
