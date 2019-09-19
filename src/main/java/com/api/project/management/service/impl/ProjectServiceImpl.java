/**
 * 
 */
package com.api.project.management.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.api.project.management.exception.ProjectCreationException;
import com.api.project.management.exception.ProjectNotFoundException;
import com.api.project.management.exception.UserCreationException;
import com.api.project.management.exception.UserNotFoundException;
import com.api.project.management.jpa.model.ParentTask;
import com.api.project.management.jpa.model.Project;
import com.api.project.management.jpa.model.Task;
import com.api.project.management.jpa.model.User;
import com.api.project.management.jpa.repository.ParentTaskRepository;
import com.api.project.management.jpa.repository.ProjectRepository;
import com.api.project.management.jpa.repository.TaskRepository;
import com.api.project.management.jpa.repository.UserRepository;
import com.api.project.management.model.ProjectDetails;
import com.api.project.management.model.UserDetails;
import com.api.project.management.request.converter.ProjectDetailsToProjectConverter;
import com.api.project.management.response.converter.ProjectToProjectDetailsConverter;
import com.api.project.management.service.ProjectService;
import com.api.project.management.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * Project Service Implementation
 * 
 * @author Narasimha Kishore Kaipa
 */
@Component
@Service
@Slf4j
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	ProjectDetailsToProjectConverter projectRequestConverter;

	@Autowired
	ProjectToProjectDetailsConverter projectResponseConverter;

	@Autowired
	UserService userService;

	@Autowired
	TaskRepository taskRepository;

	@Autowired
	ParentTaskRepository parentTaskRepository;

	@Autowired
	UserRepository userRepository;

	@Override
	public ProjectDetails createProject(ProjectDetails projectDetailsRequest)
			throws UserCreationException, UserNotFoundException, ProjectCreationException {
		validateNewProjectData(projectDetailsRequest);
		// Step 1: Create Project Data
		Project projectDataResponse = projectRepository.save(projectRequestConverter.convert(projectDetailsRequest));
		ProjectDetails projectDetailsResponse = projectResponseConverter.convert(projectDataResponse);
		// Step 2: Update users table with projectId References
		updateProjectIdReferencesForUsers(projectDetailsRequest, projectDataResponse);
		projectDetailsResponse.setUser(projectDetailsRequest.getUser());
		return projectDetailsResponse;
	}

	/**
	 * Updates ProjectId references in users table for assigned User
	 * 
	 * @param projectDetailsRequest
	 * @param projectData
	 * @throws UserCreationException
	 * @throws UserNotFoundException
	 */
	private void updateProjectIdReferencesForUsers(ProjectDetails projectDetailsRequest, Project projectDataResponse)
			throws UserCreationException, UserNotFoundException {
		if (null != projectDetailsRequest.getUser()) {
			int userId = projectDetailsRequest.getUser().getUserId();
			Optional<User> userDataOpt = userRepository.findById(userId);
			if (!userDataOpt.isPresent()) {
				log.error("User with userId " + userId + " not found");
				throw new UserNotFoundException(userId);
			}
			User userData = userDataOpt.get();
			userData.setProject(projectDataResponse);
			userRepository.save(userData);
		}
	}

	/**
	 * validates new project data and throws error if there is validation error
	 * 
	 * @param projectRequest
	 * @throws ProjectCreationException
	 */
	private void validateNewProjectData(ProjectDetails projectRequest) throws ProjectCreationException {
		if (StringUtils.isBlank(projectRequest.getProjectDesc())) {
			log.error("Project Creation validation failed  projectDesc is blank");
			throw new ProjectCreationException("projectDesc");
		}

		if (null != projectRequest.getStartDate()) {
			if (null == projectRequest.getEndDate()) {
				log.error("Project Creation validation failed  endDate is null");
				throw new ProjectCreationException("endDate");
			}
			// Make sure endDate is always greater than startDate by 1 day
			else if (!validateProjectDates(projectRequest.getStartDate(), projectRequest.getEndDate())) {
				log.error("Project Creation validation failed  endDate is not after startDate");
				throw new ProjectCreationException("endDate");
			}
		} else if ((null == projectRequest.getStartDate()) && (null == projectRequest.getEndDate())) {
			Date todayDate = new Date();
			LocalDateTime localDateTimeTomorrow = LocalDateTime.from(todayDate.toInstant()).plusDays(1);
			Date tomorrowDate = Date.from(localDateTimeTomorrow.atZone(ZoneId.systemDefault()).toInstant());
			projectRequest.setStartDate(todayDate);
			projectRequest.setEndDate(tomorrowDate);
		}
	}

	/**
	 * Validates endDate and startDate , returns true only if endDate is after
	 * startDate
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private boolean validateProjectDates(Date startDate, Date endDate) {
		LocalDate startDt = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate endDt = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return endDt.isAfter(startDt);
	}

	@Override
	public ProjectDetails findProjectByProjectId(int projectId) throws ProjectNotFoundException {
		Optional<Project> projectData = projectRepository.findById(projectId);
		if (!projectData.isPresent()) {
			log.error("Project with projectId " + projectId + " not found");
			throw new ProjectNotFoundException(projectId);
		}
		ProjectDetails projectDetails = projectResponseConverter.convert(projectData.get());
		projectDetails.setUser(findUserByProjectId(projectDetails.getProjectId()));
		return projectDetails;
	}

	@Override
	public List<ProjectDetails> findAllProjects() {
		List<ProjectDetails> projectDetailsList = new ArrayList<ProjectDetails>();
		Iterable<Project> projectDataList = projectRepository.findAll();
		for (Project projectData : projectDataList) {
			ProjectDetails projectDetails = projectResponseConverter.convert(projectData);
			projectDetails.setUser(findUserByProjectId(projectData.getProjectId()));
			projectDetailsList.add(projectDetails);
		}
		return projectDetailsList;
	}

	/**
	 * Finds userDetails by projectId
	 * 
	 * @param projectId
	 * @return
	 */
	private UserDetails findUserByProjectId(int projectId) {
		for (UserDetails userDetails : userService.findAllUsers()) {
			if ((null != userDetails.getProject()) && (userDetails.getProject().getProjectId() == projectId)) {
				return userDetails;
			}
		}
		return null;
	}

	@Override
	public ProjectDetails updateProjectDetails(ProjectDetails projectDetailsRequest)
			throws ProjectNotFoundException, ProjectCreationException, UserCreationException, UserNotFoundException {
		findProjectByProjectId(projectDetailsRequest.getProjectId());
		// Step 1: Create Project Data
		Project projectDataResponse = projectRepository.save(projectRequestConverter.convert(projectDetailsRequest));
		ProjectDetails projectDetailsResponse = projectResponseConverter.convert(projectDataResponse);
		// Step 2: Update users table with projectId References
		updateProjectIdReferencesForUsers(projectDetailsRequest, projectDataResponse);
		projectDetailsResponse.setUser(projectDetailsRequest.getUser());
		return projectDetailsResponse;
	}

	@Override
	public void deleteProject(ProjectDetails projectRequest)
			throws ProjectNotFoundException, UserCreationException, UserNotFoundException {
		// Step 1: find Project via projectId
		findProjectByProjectId(projectRequest.getProjectId());

		// Step 2: deletes Project if matching projectId found in DB
		projectRepository.delete(projectRequestConverter.convert(projectRequest));

		// Step 3: nullify projectId references for projectId in users table
		deleteProjectIdReferenceFromUser(projectRequest.getProjectId());

		// Step 4: delete all tasks from Task table with matching projectId
		deleteTaskProjectIdReferences(projectRequest.getProjectId());

		// Step 5: delete all parentTasks from ParentTask table with matching projectId
		deleteParentTasksForProjectId(projectRequest.getProjectId());
	}

	/**
	 * Deletes all parentTasks for a given projectId
	 * 
	 * @param projectId
	 */
	private void deleteParentTasksForProjectId(int projectId) {
		for (ParentTask parentTaskData : parentTaskRepository.findAll()) {
			if ((null != parentTaskData.getProject()) && (parentTaskData.getProject().getProjectId() == projectId)) {
				parentTaskRepository.delete(parentTaskData);
			}
		}
	}

	@Override
	public void deleteProjectById(int projectId)
			throws ProjectNotFoundException, UserCreationException, UserNotFoundException {
		// Step 1: find Project via projectId
		findProjectByProjectId(projectId);

		// Step 2: deletes Project if matching projectId found in DB
		projectRepository.deleteById(projectId);

		// Step 3: nullify projectId references for projectId in users table
		deleteProjectIdReferenceFromUser(projectId);

		// Step 4: delete all tasks from Task table with matching projectId
		deleteTaskProjectIdReferences(projectId);

		// Step 5: delete all parentTasks from ParentTask table with matching projectId
		deleteParentTasksForProjectId(projectId);
	}

	/**
	 * Deletes projectId reference from user table
	 * 
	 * @param projectId
	 * @throws UserNotFoundException
	 * @throws UserCreationException
	 */
	private void deleteProjectIdReferenceFromUser(int projectId) throws UserCreationException, UserNotFoundException {
		for (UserDetails userDetails : userService.findAllUsers()) {
			if ((null != userDetails.getProject()) && (userDetails.getProject().getProjectId() == projectId)) {
				userDetails.setProject(null);
				userService.updateUser(userDetails);
			}
		}
	}

	/**
	 * Deletes all tasks associated to projectId
	 * 
	 * @param projectId
	 */
	private void deleteTaskProjectIdReferences(int projectId) {
		for (Task taskData : taskRepository.findAll()) {
			if ((null != taskData.getProject()) && (taskData.getProject().getProjectId() == projectId)) {
				taskRepository.delete(taskData);
			}
		}
	}
}
