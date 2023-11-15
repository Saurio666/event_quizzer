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
@Table(name = "event_authorized_user", catalog = EntityConstants.CATALOG, schema = EntityConstants.SCHEMA_EVENT)
public class EventAuthorizedUser extends BaseEntity {
	@Id
	@Column(name = "event_authorized_id")
	@Getter
	@Setter
	public Long id;
	
	@Column(name = "user_id")
	@Getter
	@Setter
	private String userId;
	
	@Column(name = "user_name")
	@Getter
	@Setter
	private String userName;
	
	@Column(name = "user_email")
	@Getter
	@Setter
	private String userEmail;
	
	@Column(name = "user_company_name")
	@Getter
	@Setter
	private String userCompanyName;

	@Column(name = "event_id")
	@Getter
	@Setter
	private Long eventId;
	
	@Column(name = "company_location")
	@Getter
	@Setter
	private String companyLocation;
	
	@Column(name = "user_position")
	@Getter
	@Setter
	private String userPosition;
	
	@Column(name = "user_contact_phone")
	@Getter
	@Setter
	private String userContactPhone;
}
