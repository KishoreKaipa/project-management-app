package com.api.project.management.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Project Model
 * 
 * @author Narasimha Kishore Kaipa
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
public class ProjectDetails {

	@JsonProperty("projectId")
	private int projectId;

	@JsonProperty("endDate")
	private Date endDate;

	@JsonProperty("priority")
	private int priority;

	@JsonProperty("projectDesc")
	private String projectDesc;

	@JsonProperty("startDate")
	private Date startDate;

	@JsonProperty("user")
	private UserDetails user;
}