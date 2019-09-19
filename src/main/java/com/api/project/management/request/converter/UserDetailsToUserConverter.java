/**
 * 
 */
package com.api.project.management.request.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.api.project.management.jpa.model.Project;
import com.api.project.management.jpa.model.Task;
import com.api.project.management.jpa.model.User;
import com.api.project.management.model.ProjectDetails;
import com.api.project.management.model.TaskDetails;
import com.api.project.management.model.UserDetails;

/**
 * UserDetails to User Request Converter
 * 
 * @author Narasimha Kishore Kaipa
 *
 */
@Component
public class UserDetailsToUserConverter implements Converter<UserDetails, User> {

	@Override
	public User convert(UserDetails userDetails) {
		User userData = new User();
		if (userDetails.getUserId() > 0) {
			userData.setUserId(userDetails.getUserId());
		}
		userData.setFirstName(userDetails.getFirstName());
		userData.setLastName(userDetails.getLastName());
		userData.setEmployeeId(userDetails.getEmployeeId());
		if (null != userDetails.getProject()) {
			ProjectDetails projectDetails = userDetails.getProject();
			Project projectData = new Project();
			projectData.setProjectId(projectDetails.getProjectId());
			projectData.setProject(projectDetails.getProjectDesc());
			projectData.setPriority(projectDetails.getPriority());
			projectData.setStartDate(projectDetails.getStartDate());
			projectData.setEndDate(projectDetails.getEndDate());
			userData.setProject(projectData);
		}
		if (null != userDetails.getTask()) {
			TaskDetails taskDetails = userDetails.getTask();
			Task taskData = new Task();
			taskData.setTaskId(taskDetails.getTaskId());
			taskData.setTaskDescription(taskDetails.getTaskDescription());
			taskData.setStatus(taskDetails.getStatus());
			taskData.setPriority(taskDetails.getPriority());
			taskData.setStartDate(taskDetails.getStartDate());
			taskData.setEndDate(taskDetails.getEndDate());
			taskData.setParentTask(taskDetails.getParentTask());
			taskData.setProject(taskDetails.getProject());
			userData.setTask(taskData);
		}
		return userData;
	}
}