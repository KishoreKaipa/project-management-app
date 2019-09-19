package com.api.project.management.jpa.model;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * The persistent class for the task database table.
 * 
 * @author Narasimha Kishore Kaipa
 * 
 */
@Entity
@Table(name = "task")
@NamedQuery(name = "Task.findAll", query = "SELECT t FROM Task t")
@Getter
@Setter
@NoArgsConstructor
public class Task implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "task_id", unique = true, nullable = false)
	private int taskId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "priority")
	private int priority;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_date", nullable = false)
	private Date startDate;

	@Column(name = "status", nullable = false, length = 200)
	private String status;

	@Column(name = "task_description", nullable = false, length = 200)
	private String taskDescription;

	// uni-directional many-to-one association to ParentTask
	@ManyToOne
	@JoinColumn(name = "parent_id")
	private ParentTask parentTask;

	// uni-directional many-to-one association to Project
	@ManyToOne
	@JoinColumn(name = "project_id", nullable = false)
	private Project project;
}