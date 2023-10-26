package com.sauriotech.eventquizzer.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author SAURIOTech Solutions.
 *
 */
@Entity
@Table(name = "quizz", catalog = EntityConstants.CATALOG, schema = EntityConstants.SCHEMA_EVENT)
public class Quizz extends BaseEntity {
	@Id
	@Column(name = "quizz_id")
	@Getter
	@Setter
	public Long id;
	
	@Column(name = "quizz_title")
	@Getter
	@Setter
	private String title;
	
	@Column(name = "quizz_description")
	@Getter
	@Setter
	private String description;
	
	@Column(name = "quizz_image_url")
	@Getter
	@Setter
	private String imageUrl;
	
	@Column(name = "quizz_show_score_finish")
	@Getter
	@Setter
	private int showScoreOnFinish;
	
	@Column(name = "quizz_min_score_for_pass")
	@Getter
	@Setter
	private BigDecimal minScoreForPass;
	
	@Column(name = "quizz_start_date", updatable = false, insertable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@Getter
	@Setter
	private Date quizzStartDate;
	@Column(name = "quizz_end_date", updatable = false, insertable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@Getter
	@Setter
	private Date quizzEndDate;
	
	@Column(name = "quizz_attempts_per_user")
	@Getter
	@Setter
	private int attemptsPerUser;
	
	@Column(name = "quizz_is_anonymous")
	@Getter
	@Setter
	private int isAnonymous;

	@Column(name = "quizz_questions_random_sort")
	@Getter
	@Setter
	private int questionsRandomSort;

	@Column(name = "quizz_requires_authorization")
	@Getter
	@Setter
	private int requiresAuthorization;
	
	@Column(name = "event_id")
	@Getter
	@Setter
	private Long eventId;
}
