/**
 * 
 */
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
@Table(name = "question", catalog = EntityConstants.CATALOG, schema = EntityConstants.SCHEMA_EVENT)
public class Question extends BaseEntity{
	@Id
	@Column(name = "question_id")
	@Getter
	@Setter
	public Long id;
	
	@Column(name = "question_text")
	@Getter
	@Setter
	public String question;
	
	@Column(name = "question_answers_random_sort")
	@Getter
	@Setter
	public int answersRandomSort;
	
	@Column(name = "question_type")
	@Getter
	@Setter
	public String type;	
	
	@Column(name = "quizz_id")
	@Getter
	@Setter
	public Long quizzId;

	@Column(name = "question_id_parent")
	@Getter
	@Setter
	public Long parentQuestionId;
}
