package com.sauriotech.eventquizzer.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * @author SAURIOTech Solutions.
 *
 */
@Entity
@Table(name = "answer", catalog = EntityConstants.CATALOG, schema = EntityConstants.SCHEMA_EVENT)
public class Answer extends BaseEntity {
	@Id
	@Column(name = "answer_id")
	@Getter
	@Setter	
	public Long id;
	
	@Column(name = "answer_text")
	@Getter
	@Setter
	public String answer;
	
	@Column(name = "answer_weight")
	@Getter
	@Setter
	public Double weight;	
	
	
	@Column(name = "question_id")
	@Getter
	@Setter
	public Long questionId;
}
