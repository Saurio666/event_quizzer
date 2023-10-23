/**
 * 
 */
package com.sauriotech.eventquizzer.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

/**
 * @author SAURIOTech Solutions.
 *
 */
@MappedSuperclass
public class BaseEntity {
	@Basic(optional = false)
	@Column(name = "status")
	@Getter
	@Setter
	private String status;
	
	@Column(name = "register_date", updatable = false, insertable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@Getter
	@Setter
	private Date registerDate;
	
	@Column(name = "register_user", updatable = false, insertable = false)
	@Getter
	@Setter
	private String registerDbUser;
	
	@Column(name = "update_date")
	@Temporal(TemporalType.TIMESTAMP)
	@Getter
	@Setter
	private Date updateDate;
	
	@Column(name = "updater_user", updatable = false, insertable = false)
	@Getter
	@Setter
	private String updateDbUser;
}
