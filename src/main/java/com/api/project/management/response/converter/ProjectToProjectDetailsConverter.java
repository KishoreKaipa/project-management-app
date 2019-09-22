/**
 * 
 */
package com.api.project.management.response.converter;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.api.project.management.jpa.model.Project;
import com.api.project.management.model.ProjectDetails;
import com.api.project.management.util.DateUtils;

/**
 * Project to ProjectDetails Response Converter
 * @author Narasimha Kishore Kaipa
 *
 */
@Component
public class ProjectToProjectDetailsConverter implements Converter<Project, ProjectDetails> {
	@Autowired
	DateUtils dateUtils;

	@Override
	public ProjectDetails convert(Project projectData) {
		ProjectDetails projectDetails = new ProjectDetails();
		projectDetails.setProjectId(projectData.getProjectId());
		projectDetails.setProjectDesc(projectData.getProject());
		projectDetails.setPriority(projectData.getPriority());
		try {
			projectDetails.setStartDate(dateUtils.getDateWithoutTimestamp(projectData.getStartDate()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			projectDetails.setEndDate(dateUtils.getDateWithoutTimestamp(projectData.getEndDate()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return projectDetails;
	}
}
