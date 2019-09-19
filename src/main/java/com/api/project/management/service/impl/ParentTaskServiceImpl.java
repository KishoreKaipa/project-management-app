/**
 * 
 */
package com.api.project.management.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.api.project.management.exception.ParentTaskCreationException;
import com.api.project.management.exception.ParentTaskNotFoundException;
import com.api.project.management.exception.ProjectNotFoundException;
import com.api.project.management.exception.TaskCreationException;
import com.api.project.management.exception.TaskNotFoundException;
import com.api.project.management.exception.UserCreationException;
import com.api.project.management.exception.UserNotFoundException;
import com.api.project.management.jpa.model.ParentTask;
import com.api.project.management.jpa.repository.ParentTaskRepository;
import com.api.project.management.model.ParentTaskDetails;
import com.api.project.management.model.ProjectDetails;
import com.api.project.management.model.TaskDetails;
import com.api.project.management.request.converter.ParentTaskDetailsToParentTaskConverter;
import com.api.project.management.response.converter.ParentTaskToParentTaskDetailsConverter;
import com.api.project.management.service.ParentTaskService;
import com.api.project.management.service.ProjectService;
import com.api.project.management.service.TaskService;

import lombok.extern.slf4j.Slf4j;

/**
 * Parent Task Service Implementation
 * 
 * @author Narasimha Kishore Kaipa
 *
 */
@Component
@Service
@Slf4j
public class ParentTaskServiceImpl implements ParentTaskService {

	@Autowired
	ParentTaskRepository parentTaskRepository;

	@Autowired
	TaskService taskService;

	@Autowired
	ProjectService projectService;

	@Autowired
	ParentTaskDetailsToParentTaskConverter parentTaskRequestConverter;

	@Autowired
	ParentTaskToParentTaskDetailsConverter parentTaskResponseConverter;

	@Override
	public ParentTaskDetails createParentTask(ParentTaskDetails parentTaskDetailsRequest)
			throws ProjectNotFoundException, ParentTaskCreationException {
		// validates parentTask description and projectId
		validateParentTaskDetails(parentTaskDetailsRequest);
		ParentTaskDetails parentTaskDetailsResponse = parentTaskResponseConverter
				.convert(parentTaskRepository.save(parentTaskRequestConverter.convert(parentTaskDetailsRequest)));
		// populate Project Data accordingly with missing userDetails
		populateProjectData(parentTaskDetailsResponse);
		return parentTaskDetailsResponse;
	}

	/**
	 * populate Project Data for missing userDetails
	 * @param parentTaskDetailsResponse
	 * @throws ProjectNotFoundException
	 */
	private void populateProjectData(ParentTaskDetails parentTaskDetailsResponse) throws ProjectNotFoundException {
		if (null != parentTaskDetailsResponse.getProject()) {
			ProjectDetails projectDetails = projectService
					.findProjectByProjectId(parentTaskDetailsResponse.getProject().getProjectId());
			parentTaskDetailsResponse.setProject(projectDetails);
		}

	}

	@Override
	public ParentTaskDetails updateParentTask(ParentTaskDetails parentTaskDetailsRequest)
			throws ProjectNotFoundException, ParentTaskCreationException, ParentTaskNotFoundException {
		findParentTaskDetailsById(parentTaskDetailsRequest.getParentId());
		// validates parentTask description and projectId
		validateParentTaskDetails(parentTaskDetailsRequest);
		ParentTaskDetails parentTaskDetailsResponse = parentTaskResponseConverter
				.convert(parentTaskRepository.save(parentTaskRequestConverter.convert(parentTaskDetailsRequest)));
		// populate Project Data accordingly with missing userDetails
		populateProjectData(parentTaskDetailsResponse);
		return parentTaskDetailsResponse;
	}

	@Override
	public ParentTaskDetails findParentTaskDetailsById(int parentTaskId)
			throws ParentTaskNotFoundException, ProjectNotFoundException {
		Optional<ParentTask> parentTask = parentTaskRepository.findById(parentTaskId);
		if (!parentTask.isPresent()) {
			log.error("Parent Task with taskId " + parentTaskId + " not found");
			throw new ParentTaskNotFoundException(parentTaskId);
		}
		ParentTaskDetails parentTaskDetailsResponse = parentTaskResponseConverter.convert(parentTask.get());
		// populate Project Data accordingly with missing userDetails
		populateProjectData(parentTaskDetailsResponse);
		return parentTaskDetailsResponse;
	}

	@Override
	public List<ParentTaskDetails> findAllParentTaskDetails() throws ProjectNotFoundException {
		List<ParentTaskDetails> parentTaskDetailList = new ArrayList<ParentTaskDetails>();
		Iterable<ParentTask> parentTaskDataList = parentTaskRepository.findAll();
		for (ParentTask parentTask : parentTaskDataList) {
			ParentTaskDetails parentTaskDetailsResponse = parentTaskResponseConverter.convert(parentTask);
			// populate Project Data accordingly with missing userDetails
			populateProjectData(parentTaskDetailsResponse);
			parentTaskDetailList.add(parentTaskDetailsResponse);
		}
		return parentTaskDetailList;
	}

	@Override
	public void deleteParentTask(ParentTaskDetails parentTaskDetailsRequest) throws ParentTaskNotFoundException,
			ProjectNotFoundException, TaskNotFoundException, UserCreationException, UserNotFoundException {
		// find parentTask via parentTaskId
		findParentTaskDetailsById(parentTaskDetailsRequest.getParentId());
		// delete all tasks associated with this parentTaskId
		deleteParentTaskIdReferences(parentTaskDetailsRequest.getParentId());
		// deletes parentTask if matching parentTaskId found in DB
		parentTaskRepository.delete(parentTaskRequestConverter.convert(parentTaskDetailsRequest));
	}

	@Override
	public void deleteParentTaskByTaskId(int parentTaskId) throws ParentTaskNotFoundException, ProjectNotFoundException,
			TaskNotFoundException, UserCreationException, UserNotFoundException {
		// find parentTask via parentTaskId
		findParentTaskDetailsById(parentTaskId);
		// delete all tasks associated with this parentTaskId
		deleteParentTaskIdReferences(parentTaskId);
		// deletes parentTask if matching parentTaskId found in DB
		parentTaskRepository.deleteById(parentTaskId);
	}

	/**
	 * validates new parent task data and throws error if there is validation error
	 * 
	 * @param parentTaskDetailsRequest
	 * @throws ParentTaskCreationException
	 * @throws ParentTaskCreationException
	 */
	private void validateParentTaskDetails(ParentTaskDetails parentTaskDetailsRequest)
			throws ParentTaskCreationException {
		if (StringUtils.isBlank(parentTaskDetailsRequest.getParentTask())) {
			log.error("Parent Task Creation validation failed  parent task description is blank");
			throw new ParentTaskCreationException("parentTaskDescription");
		}
		if (null == parentTaskDetailsRequest.getProject()) {
			log.error("Parent Task Creation validation failed  project cannot be blank");
			throw new ParentTaskCreationException("project");
		}
	}

	/**
	 * Deletes parentTaskId references from Task table
	 * 
	 * @param parentTaskId
	 * @throws UserCreationException
	 * @throws UserNotFoundException
	 * @throws TaskCreationException
	 * @throws TaskNotFoundException
	 */
	private void deleteParentTaskIdReferences(int parentTaskId)
			throws TaskNotFoundException, UserCreationException, UserNotFoundException {
		// Update projectId to 0 for any user
		List<TaskDetails> taskDetailsList = taskService.findAllTaskDetails();
		for (TaskDetails taskDetail : taskDetailsList) {
			if ((null != taskDetail.getParentTask()) && (taskDetail.getParentTask().getParentId() == parentTaskId)) {
				taskService.deleteTask(taskDetail);
			}
		}
	}

}
