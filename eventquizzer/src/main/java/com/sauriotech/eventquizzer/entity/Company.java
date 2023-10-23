/**
 * 
 */
package com.sauriotech.eventquizzer.entity;

import javax.persistence.Basic;
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
@Table(name = "company", catalog = EntityConstants.CATALOG, schema = EntityConstants.SCHEMA_EVENT)
public class Company extends BaseEntity {
	@Id
	@Basic(optional = false)
	@Column(name = "company_id")
	@Getter
	@Setter
	private Long id;
	
	@Column(name = "company_name")
	@Getter
	@Setter	
	private String name;
	
	@Column(name = "company_description")
	@Getter
	@Setter
	private String description;
	
	@Column(name = "company_url")
	@Getter
	@Setter
	private String url;
	
	@Column(name = "company_logo_path")
	@Getter
	@Setter
	private String logo;
	
}
