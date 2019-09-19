package com.api.project.management.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * User Model
 * 
 * @author Narasimha Kishore Kaipa
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
public class UserDetails {

	@JsonProperty("userId")
	private int userId;

	@JsonProperty("employeeId")
	private int employeeId;

	@JsonProperty("firstName")
	private String firstName;

	@JsonProperty("lastName")
	private String lastName;

	@JsonProperty("project")
	private ProjectDetails project;

	@JsonProperty("task")
	private TaskDetails task;
}