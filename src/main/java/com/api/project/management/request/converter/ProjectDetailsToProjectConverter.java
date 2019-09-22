/**
 * 
 */
package com.api.project.management.request.converter;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import com.api.project.management.jpa.model.Project;
import com.api.project.management.model.ProjectDetails;
import com.api.project.management.util.DateUtils;

/**
 * ProjectDetails to Project Request Converter
 * 
 * @author Narasimha Kishore Kaipa
 *
 */
@Component
public class ProjectDetailsToProjectConverter implements Converter<ProjectDetails, Project> {

	@Autowired
	DateUtils dateUtils;
	
	@Override
	public Project convert(ProjectDetails projectDetails) {
		Project projectData = new Project();
		if (projectDetails.getProjectId() > 0) {
			projectData.setProjectId(projectDetails.getProjectId());
		}
		projectData.setProject(projectDetails.getProjectDesc());
		projectData.setPriority(projectDetails.getPriority());
		try {
			projectData.setStartDate(dateUtils.getDateWithoutTimestamp(projectDetails.getStartDate()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			projectData.setEndDate(dateUtils.getDateWithoutTimestamp(projectDetails.getEndDate()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return projectData;
	}
}
