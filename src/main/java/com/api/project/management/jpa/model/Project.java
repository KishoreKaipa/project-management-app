package com.api.project.management.jpa.model;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * The persistent class for the project database table.
 * @author Narasimha Kishore Kaipa
 * 
 */
@Entity
@Table(name = "project")
@NamedQuery(name = "Project.findAll", query = "SELECT p FROM Project p")
@Getter
@Setter
@NoArgsConstructor
public class Project implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "project_id", unique = true, nullable = false)
	private int projectId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_date", nullable = false)
	private Date endDate;

	@Column(name = "priority")
	private int priority;

	@Column(name = "project", nullable = false, length = 200)
	private String project;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_date", nullable = false)
	private Date startDate;

}