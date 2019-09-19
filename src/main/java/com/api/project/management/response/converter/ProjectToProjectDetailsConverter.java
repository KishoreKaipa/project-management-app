/**
 * 
 */
package com.api.project.management.response.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.api.project.management.jpa.model.Project;
import com.api.project.management.model.ProjectDetails;

/**
 * Project to ProjectDetails Response Converter
 * 
 * @author Narasimha Kishore Kaipa
 *
 */
@Component
public class ProjectToProjectDetailsConverter implements Converter<Project, ProjectDetails> {

	@Override
	public ProjectDetails convert(Project projectData) {
		ProjectDetails projectDetails = new ProjectDetails();
		projectDetails.setProjectId(projectData.getProjectId());
		projectDetails.setProjectDesc(projectData.getProject());
		projectDetails.setPriority(projectData.getPriority());
		projectDetails.setStartDate(projectData.getStartDate());
		projectDetails.setEndDate(projectData.getEndDate());
		return projectDetails;
	}
}
