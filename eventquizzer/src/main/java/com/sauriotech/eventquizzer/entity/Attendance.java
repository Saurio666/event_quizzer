package com.sauriotech.eventquizzer.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * @author SAURIOTech Solutions.
 *
 */
@Entity
@Table(name = "attendance", catalog = EntityConstants.CATALOG, schema = EntityConstants.SCHEMA_EVENT)
public class Attendance extends BaseEntity {
	@Id
	@Column(name = "attendance_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Setter
	public Long id;
	
	@Column(name = "user_id")
	@Getter
	@Setter
	private String userId;
	
	@Column(name = "attendance_date")
	@Getter
	@Setter
	private String attendanceDate;
	
	
	@Column(name = "event_id")
	@Getter
	@Setter
	public Long eventId;
}
