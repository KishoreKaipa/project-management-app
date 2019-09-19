/**
 * 
 */
package com.api.project.management.request.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import com.api.project.management.jpa.model.Project;
import com.api.project.management.model.ProjectDetails;

/**
 * ProjectDetails to Project Request Converter
 * 
 * @author Narasimha Kishore Kaipa
 *
 */
@Component
public class ProjectDetailsToProjectConverter implements Converter<ProjectDetails, Project> {

	@Override
	public Project convert(ProjectDetails projectDetails) {
		Project projectData = new Project();
		if (projectDetails.getProjectId() > 0) {
			projectData.setProjectId(projectDetails.getProjectId());
		}
		projectData.setProject(projectDetails.getProjectDesc());
		projectData.setPriority(projectDetails.getPriority());
		projectData.setStartDate(projectDetails.getStartDate());
		projectData.setEndDate(projectDetails.getEndDate());
		return projectData;
	}
}
