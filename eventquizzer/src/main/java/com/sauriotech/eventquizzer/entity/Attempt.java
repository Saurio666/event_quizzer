package com.sauriotech.eventquizzer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

/**
 * @author SAURIOTech Solutions.
 *
 */
@Entity
@Table(name = "attempt", catalog = EntityConstants.CATALOG, schema = EntityConstants.SCHEMA_EVENT)
public class Attempt extends BaseEntity {
	@Id
	@Column(name = "attempt_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Setter
	public Long id;
	
	@Column(name = "user_id")
	@Getter
	@Setter	
	private String userId;
	
	@Column(name = "quizz_id")
	@Getter
	@Setter
	private Long quizId;
	
	@Column(name = "submited_data")
	@Getter
	@Setter
	private String submitedData;
	
	@Column(name = "attempt_qualified_data")
	@Getter
	@Setter
	private String qualifiedData;
	
	@Column(name = "attempt_score")
	@Getter
	@Setter
	private Double attemptScore;
	
	@Column(name = "attempt_start", updatable = false, insertable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@Getter
	@Setter
	private Date attemptStart;
	
	@Column(name = "attempt_end", updatable = true, insertable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@Getter
	@Setter
	private Date attemptEnd;
	
	@Column(name = "attempt_status", updatable = true, insertable = false)
	@Getter
	@Setter
	private String attemptStatus;
}
