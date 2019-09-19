/**
 * 
 */
package com.api.project.management.response.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.api.project.management.jpa.model.ParentTask;
import com.api.project.management.jpa.model.Project;
import com.api.project.management.model.ParentTaskDetails;
import com.api.project.management.model.ProjectDetails;

/**
 * ParentTask to ParentTaskDetails Response Converter
 * 
 * @author Narasimha Kishore Kaipa
 *
 */
@Component
public class ParentTaskToParentTaskDetailsConverter implements Converter<ParentTask, ParentTaskDetails> {

	@Override
	public ParentTaskDetails convert(ParentTask parentTaskData) {
		ParentTaskDetails parentTaskDetails = new ParentTaskDetails();
		parentTaskDetails.setParentId(parentTaskData.getParentId());
		if (null != parentTaskData.getProject()) {
			ProjectDetails projectDetails = new ProjectDetails();
			Project projectData = parentTaskData.getProject();
			projectDetails.setProjectId(projectData.getProjectId());
			projectDetails.setProjectDesc(projectData.getProject());
			projectDetails.setPriority(projectData.getPriority());
			projectDetails.setStartDate(projectData.getStartDate());
			projectDetails.setEndDate(projectData.getEndDate());
			projectDetails.setUser(null);
			parentTaskDetails.setProject(projectDetails);
		}
		parentTaskDetails.setParentTask(parentTaskData.getParentTask());
		return parentTaskDetails;
	}
}
