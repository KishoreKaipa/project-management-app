package com.api.project.management.model;

import java.util.Date;

import com.api.project.management.jpa.model.ParentTask;
import com.api.project.management.jpa.model.Project;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Task Model
 * 
 * @author Narasimha Kishore Kaipa
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
public class TaskDetails {
	@JsonProperty("taskId")
	private int taskId;

	@JsonProperty("endDate")
	private Date endDate;

	@JsonProperty("priority")
	private int priority;

	@JsonProperty("startDate")
	private Date startDate;

	@JsonProperty("status")
	private String status;

	@JsonProperty("taskDescription")
	private String taskDescription;

	@JsonProperty("parentTask")
	private ParentTask parentTask;

	@JsonProperty("projectId")
	private Project project;
	
	@JsonProperty("user")
	private UserDetails user;
}