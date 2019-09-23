/**
 * 
 */
package com.api.project.management.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.project.management.jpa.model.ParentTask;
import com.api.project.management.jpa.model.Project;
import com.api.project.management.jpa.model.Task;
import com.api.project.management.jpa.model.User;
import com.api.project.management.jpa.repository.TaskRepository;
import com.api.project.management.jpa.repository.UserRepository;
import com.api.project.management.model.ParentTaskDetails;
import com.api.project.management.model.ProjectDetails;
import com.api.project.management.model.TaskDetails;
import com.api.project.management.model.UserDetails;

/**
 * MySQLDB to API Response Conversion Utils
 * 
 * @author Narasimha Kishore Kaipa
 *
 */
@Component
public class ResponseConversionUtils {

	@Autowired
	UserRepository userRepository;

	@Autowired
	TaskRepository taskRepository;

	/**
	 * 
	 * @param projectData
	 * @return
	 */
	public ProjectDetails populateProjectDetailsFromProjectData(Project projectData, boolean userDetailsCheck,
			boolean taskListCheck) {
		ProjectDetails projectDetails = new ProjectDetails();
		projectDetails.setProjectId(projectData.getProjectId());
		projectDetails.setProjectDescription(projectData.getProject());
		projectDetails.setPriority(projectData.getPriority());
		if (null != projectData.getStartDate()) {
			projectDetails.setStartDate(projectData.getStartDate());
		}
		if (null != projectData.getEndDate()) {
			projectDetails.setEndDate(projectData.getEndDate());
		}
		if (userDetailsCheck) {
			projectDetails.setUserDetails(populateUserDetailsFromProjectData(projectData.getProjectId()));
		}
		if (taskListCheck) {
			projectDetails.setTaskList(populateTaskListAssociatedToProject(projectData.getProjectId()));
		}
		return projectDetails;
	}

	/**
	 * Returns taskDetailsList associated to given projectData
	 * 
	 * @param projectId
	 * @return taskDetailsList
	 */
	public List<TaskDetails> populateTaskListAssociatedToProject(int projectId) {
		List<TaskDetails> taskDetailsList = new ArrayList<TaskDetails>();
		for (Task taskData : taskRepository.findAll()) {
			if ((null != taskData.getProject()) && (taskData.getProject().getProjectId() == projectId)) {
				taskDetailsList.add(populateTaskDetailsFromTaskData(taskData, false, false, false));
			}
		}

		return taskDetailsList;
	}

	/**
	 * Populate matching userDetails from projectId
	 * 
	 * @param projectId
	 * @return
	 */
	public UserDetails populateUserDetailsFromProjectData(int projectId) {
		for (User userData : userRepository.findAll()) {
			if ((null != userData.getProject()) && (userData.getProject().getProjectId() == projectId)) {
				return populateUserDetailsFromUserData(userData, false, false);
			}
		}
		return null;
	}

	/**
	 * Populates userDetails from userData
	 * 
	 * @param userData
	 * @param projectDetailsCheck
	 * @param taskDetailsCheck
	 * @return userDetails
	 */
	public UserDetails populateUserDetailsFromUserData(User userData, boolean projectDetailsCheck,
			boolean taskDetailsCheck) {
		UserDetails userDetails = new UserDetails();
		userDetails.setUserId(userData.getUserId());
		userDetails.setEmployeeId(userData.getEmployeeId());
		userDetails.setFirstName(userData.getFirstName());
		userDetails.setLastName(userData.getLastName());
		if ((null != userData.getProject()) && projectDetailsCheck) {
			userDetails.setProjectDetails(populateProjectDetailsFromProjectData(userData.getProject(), false, false));
		}
		if ((null != userData.getTask()) && taskDetailsCheck) {
			userDetails.setTaskDetails(populateTaskDetailsFromTaskData(userData.getTask(), false, false, false));
		}
		return userDetails;
	}

	/**
	 * Populates taskDetails from taskData
	 * 
	 * @param task
	 * @param projectDetailsCheck
	 * @param userDetailsCheck
	 * @param parentTaskDetailsCheck
	 * @return taskDetails
	 */
	public TaskDetails populateTaskDetailsFromTaskData(Task task, boolean projectDetailsCheck, boolean userDetailsCheck,
			boolean parentTaskDetailsCheck) {
		TaskDetails taskDetails = new TaskDetails();
		taskDetails.setTaskId(task.getTaskId());
		taskDetails.setTaskDescription(task.getTaskDescription());
		taskDetails.setTaskStatus(task.getStatus());
		taskDetails.setPriority(task.getPriority());
		if (null != task.getStartDate()) {
			taskDetails.setStartDate(task.getStartDate());
		}
		if (null != task.getEndDate()) {
			taskDetails.setEndDate(task.getEndDate());
		}
		if ((null != task.getProject()) && projectDetailsCheck) {
			taskDetails.setProjectDetails(populateProjectDetailsFromProjectData(task.getProject(), false, false));
		}
		if (userDetailsCheck) {
			taskDetails.setUserDetails(populateUserDetailsFromTaskData(task.getTaskId()));
		}
		if ((null != task.getParentTask()) && parentTaskDetailsCheck) {
			taskDetails.setParentTaskDetails(populateParentTaskDetailsFromParentTaskData(task.getParentTask(), false));
		}
		return taskDetails;
	}

	/**
	 * Returns parentTask from parentTask
	 * 
	 * @param parentTask
	 * @param projectDetailsCheck
	 * @return parentTaskDetails
	 */
	public ParentTaskDetails populateParentTaskDetailsFromParentTaskData(ParentTask parentTaskData,
			boolean projectDetailsCheck) {
		ParentTaskDetails parentTaskDetails = new ParentTaskDetails();
		parentTaskDetails.setParentId(parentTaskData.getParentId());
		parentTaskDetails.setParentTaskDescription(parentTaskData.getParentTask());
		if ((null != parentTaskData.getProject()) && projectDetailsCheck) {
			parentTaskDetails.setProjectDetails(
					populateProjectDetailsFromProjectData(parentTaskData.getProject(), false, false));
		}
		return parentTaskDetails;
	}

	/**
	 * Returns userDetails from taskId
	 * 
	 * @param taskId
	 * @return userDetails
	 */
	public UserDetails populateUserDetailsFromTaskData(int taskId) {
		for (User userData : userRepository.findAll()) {
			if ((null != userData.getTask()) && (userData.getTask().getTaskId() == taskId)) {
				return populateUserDetailsFromUserData(userData, false, false);
			}
		}
		return null;
	}
}
