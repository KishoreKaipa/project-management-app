/**
 * 
 */
package com.api.project.management.request.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.api.project.management.jpa.model.ParentTask;
import com.api.project.management.jpa.model.Project;
import com.api.project.management.model.ParentTaskDetails;
import com.api.project.management.model.ProjectDetails;

/**
 * ParentTaskDetails to ParentTask Request Converter
 * 
 * @author Narasimha Kishore Kaipa
 *
 */
@Component
public class ParentTaskDetailsToParentTaskConverter implements Converter<ParentTaskDetails, ParentTask> {

	@Override
	public ParentTask convert(ParentTaskDetails parentTaskDetails) {
		ParentTask parentTaskData = new ParentTask();
		if (parentTaskDetails.getParentId() > 0) {
			parentTaskData.setParentId(parentTaskDetails.getParentId());
		}
		if (null != parentTaskDetails.getProject()) {
			Project projectData = new Project();
			ProjectDetails projectDetails = parentTaskDetails.getProject();
			projectData.setProjectId(projectDetails.getProjectId());
			projectData.setProject(projectDetails.getProjectDesc());
			projectData.setPriority(projectDetails.getPriority());
			projectData.setStartDate(projectDetails.getStartDate());
			projectData.setEndDate(projectDetails.getEndDate());
			parentTaskData.setProject(projectData);
		}
		parentTaskData.setParentTask(parentTaskDetails.getParentTask());
		return parentTaskData;
	}
}
