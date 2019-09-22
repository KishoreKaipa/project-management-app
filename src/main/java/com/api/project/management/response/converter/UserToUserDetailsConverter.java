/**
 * 
 */
package com.api.project.management.response.converter;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.api.project.management.jpa.model.Project;
import com.api.project.management.jpa.model.Task;
import com.api.project.management.jpa.model.User;
import com.api.project.management.model.ProjectDetails;
import com.api.project.management.model.TaskDetails;
import com.api.project.management.model.UserDetails;
import com.api.project.management.util.DateUtils;

/**
 * User to UserDetails Response Converter
 * 
 * @author Narasimha Kishore Kaipa
 *
 */
@Component
public class UserToUserDetailsConverter implements Converter<User, UserDetails> {

	@Autowired
	DateUtils dateUtils;

	@Override
	public UserDetails convert(User userData) {
		UserDetails userDetails = new UserDetails();
		userDetails.setUserId(userData.getUserId());
		userDetails.setFirstName(userData.getFirstName());
		userDetails.setLastName(userData.getLastName());
		userDetails.setEmployeeId(userData.getEmployeeId());

		if (null != userData.getProject()) {
			Project projectData = userData.getProject();
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
			userDetails.setProject(projectDetails);
		}
		if (null != userData.getTask()) {
			Task taskData = userData.getTask();
			TaskDetails taskDetails = new TaskDetails();
			taskDetails.setTaskId(taskData.getTaskId());
			taskDetails.setTaskDescription(taskData.getTaskDescription());
			taskDetails.setStatus(taskData.getStatus());
			taskDetails.setPriority(taskData.getPriority());
			try {
				taskDetails.setStartDate(dateUtils.getDateWithoutTimestamp(taskData.getStartDate()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				taskDetails.setEndDate(dateUtils.getDateWithoutTimestamp(taskData.getEndDate()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			taskDetails.setParentTask(taskData.getParentTask());
			taskDetails.setProject(taskData.getProject());
			userDetails.setTask(taskDetails);
		}
		return userDetails;
	}
}
