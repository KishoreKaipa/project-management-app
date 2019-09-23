/**
 * 
 */
package com.api.project.management.response.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.api.project.management.jpa.model.Project;
import com.api.project.management.model.ProjectDetails;
import com.api.project.management.util.ResponseConversionUtils;

/**
 * Project to ProjectDetails Response Converter
 * 
 * @author Narasimha Kishore Kaipa
 *
 */
@Component
public class ProjectToProjectDetailsConverter implements Converter<Project, ProjectDetails> {
	@Autowired
	ResponseConversionUtils responseConversionUtils;

	@Override
	public ProjectDetails convert(Project projectData) {
		return responseConversionUtils.populateProjectDetailsFromProjectData(projectData, true, true);
	}
}
