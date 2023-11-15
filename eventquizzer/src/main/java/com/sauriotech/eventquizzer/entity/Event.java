/**
 * 
 */
package com.sauriotech.eventquizzer.entity;

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
 * @author SAURIOTech Solutions.
 *
 */
@Entity
@Table(name = "event", catalog = EntityConstants.CATALOG, schema = EntityConstants.SCHEMA_EVENT)
public class Event extends BaseEntity {
	@Id
	@Column(name = "event_id")
	@Getter
	@Setter
	public Long id;
	
	@Column(name = "event_title")
	@Getter
	@Setter
	private String title;
	
	@Column(name = "event_short_description")
	@Getter
	@Setter
	private String shortDescription;
	
	@Column(name = "event_description")
	@Getter
	@Setter
	private String description;
	
	@Column(name = "event_image_url")
	@Getter
	@Setter
	private String imageUrl;
	
	@Column(name = "event_start_date", updatable = false, insertable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@Getter
	@Setter
	private Date eventStartDate;
	
	@Column(name = "event_end_date", updatable = false, insertable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@Getter
	@Setter
	private Date eventEndDate;
	
	@Column(name = "event_requires_authorization")
	@Getter
	@Setter
	private int requiresAuthorization;
	
	@Column(name = "event_timezone")
	@Getter
	@Setter
	private String eventTimezone;
	
	@Column(name = "company_id")
	@Getter
	@Setter
	private Long companyId;
	
	@Column(name = "event_extra_content")
	@Getter
	@Setter
	private String extraContent;
}
