/**
 * 
 */
package com.api.project.management.request.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.api.project.management.jpa.model.Project;
import com.api.project.management.model.ProjectDetails;
import com.api.project.management.util.RequestConversionUtils;

/**
 * ProjectDetails to Project Request Converter
 * 
 * @author Narasimha Kishore Kaipa
 *
 */
@Component
public class ProjectDetailsToProjectConverter implements Converter<ProjectDetails, Project> {

	@Autowired
	RequestConversionUtils requestConversionUtils;

	@Override
	public Project convert(ProjectDetails projectDetails) {
		return requestConversionUtils.populateProjectDataFromProjectDetails(projectDetails);
	}
}
