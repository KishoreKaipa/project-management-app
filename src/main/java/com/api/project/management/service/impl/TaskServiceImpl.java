/**
 * 
 */
package com.api.project.management.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.api.project.management.exception.TaskCreationException;
import com.api.project.management.exception.TaskNotFoundException;
import com.api.project.management.exception.UserCreationException;
import com.api.project.management.exception.UserNotFoundException;
import com.api.project.management.jpa.model.Task;
import com.api.project.management.jpa.repository.TaskRepository;
import com.api.project.management.model.TaskDetails;
import com.api.project.management.model.UserDetails;
import com.api.project.management.request.converter.TaskDetailsToTaskConverter;
import com.api.project.management.response.converter.TaskToTaskDetailsConverter;
import com.api.project.management.service.TaskService;
import com.api.project.management.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * Task Service Implementation for tasks module
 * 
 * @author Narasimha Kishore Kaipa
 *
 */
@Component
@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

	@Autowired
	TaskRepository taskRepository;

	@Autowired
	TaskDetailsToTaskConverter taskRequestConverter;

	@Autowired
	TaskToTaskDetailsConverter taskResponseConverter;

	@Autowired
	UserService userService;

	@Override
	public TaskDetails createTask(TaskDetails taskDetailsRequest)
			throws UserCreationException, UserNotFoundException, TaskCreationException {
		// validate Task Details
		validateTaskDetails(taskDetailsRequest);
		TaskDetails taskDetailsResponse = taskResponseConverter
				.convert(taskRepository.save(taskRequestConverter.convert(taskDetailsRequest)));
		// Map newly created taskId to user based on userId
		updateUserTaskIdReference(taskDetailsRequest, taskDetailsResponse);
		return taskDetailsResponse;
	}

	/**
	 * Update user reference for associated taskId
	 * 
	 * @param taskDetailsRequest
	 * @param taskDetailsResponse
	 * @throws UserCreationException
	 * @throws UserNotFoundException
	 */
	private void updateUserTaskIdReference(TaskDetails taskDetailsRequest, TaskDetails taskDetailsResponse)
			throws UserCreationException, UserNotFoundException {
		UserDetails userDetails = taskDetailsRequest.getUser();
		if (null != userDetails) {
			userDetails.setTask(taskDetailsResponse);
			userService.updateUser(userDetails);
			taskDetailsResponse.setUser(userDetails);
		}
	}

	/**
	 * Validates Task creation or update details
	 * 
	 * @param taskDetailsRequest
	 * @throws TaskCreationException
	 */
	private void validateTaskDetails(TaskDetails taskDetailsRequest) throws TaskCreationException {
		if (StringUtils.isBlank(taskDetailsRequest.getTaskDescription())) {
			log.error("Task Creation validation failed  taskDescription is blank");
			throw new TaskCreationException("taskDesc");
		}

		if (null != taskDetailsRequest.getStartDate()) {
			if (null == taskDetailsRequest.getEndDate()) {
				log.error("Task Creation validation failed  endDate is null");
				throw new TaskCreationException("endDate");
			}
			// Make sure endDate is always greater than startDate by 1 day
			else if (!validateTaskDates(taskDetailsRequest.getStartDate(), taskDetailsRequest.getEndDate())) {
				log.error("Task Creation validation failed  endDate is not after startDate");
				throw new TaskCreationException("endDate");
			}
		}
	}

	/**
	 * Validates endDate and startDate , returns true only if endDate is after
	 * startDate
	 * 
	 * @param startDate
	 * @param endDate
	 * @return true if endDate is greater than startDate else false
	 */
	private boolean validateTaskDates(Date startDate, Date endDate) {
		LocalDate startDt = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate endDt = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return endDt.isAfter(startDt);
	}

	@Override
	public TaskDetails updateTask(TaskDetails taskDetailsRequest)
			throws TaskCreationException, UserCreationException, UserNotFoundException, TaskNotFoundException {
		findTaskDetailsById(taskDetailsRequest.getTaskId());
		// validate Task Details
		validateTaskDetails(taskDetailsRequest);
		TaskDetails taskDetailsResponse = taskResponseConverter
				.convert(taskRepository.save(taskRequestConverter.convert(taskDetailsRequest)));
		// Map newly created taskId to user based on userId
		updateUserTaskIdReference(taskDetailsRequest, taskDetailsResponse);
		return taskDetailsResponse;
	}

	@Override
	public TaskDetails findTaskDetailsById(int taskId) throws TaskNotFoundException {
		Optional<Task> taskData = taskRepository.findById(taskId);
		if (!taskData.isPresent()) {
			log.error("Task with taskId " + taskId + " not found");
			throw new TaskNotFoundException(taskId);
		}
		TaskDetails taskDetailsResponse = taskResponseConverter.convert(taskData.get());
		// map userDetails to task
		mapUserDetailsForTask(taskDetailsResponse);
		return taskDetailsResponse;
	}

	/**
	 * Maps userDetails for chosen taskId
	 * 
	 * @param taskDetailsResponse
	 */
	private void mapUserDetailsForTask(TaskDetails taskDetailsResponse) {
		// Update projectId to 0 for any user
		List<UserDetails> userDetailsList = userService.findAllUsers();
		for (UserDetails userDetails : userDetailsList) {
			if ((null != userDetails.getTask())
					&& (userDetails.getTask().getTaskId() == taskDetailsResponse.getTaskId())) {
				taskDetailsResponse.setUser(userDetails);
			}
		}
	}

	@Override
	public List<TaskDetails> findAllTaskDetails() {
		List<TaskDetails> taskDetailsList = new ArrayList<TaskDetails>();
		Iterable<Task> taskDataList = taskRepository.findAll();
		for (Task taskData : taskDataList) {
			TaskDetails taskDetailsResponse = taskResponseConverter.convert(taskData);
			// map userDetails to task
			mapUserDetailsForTask(taskDetailsResponse);
			taskDetailsList.add(taskDetailsResponse);
		}
		return taskDetailsList;
	}

	@Override
	public void deleteTask(TaskDetails taskDetails)
			throws TaskNotFoundException, UserCreationException, UserNotFoundException {
		// find task via taskId
		findTaskDetailsById(taskDetails.getTaskId());
		// deletes Task if matching taskId found in DB
		taskRepository.delete(taskRequestConverter.convert(taskDetails));
		// nullify taskId references for taskId in users table
		deleteUserTaskIdReferences(taskDetails.getTaskId());
	}

	/**
	 * Deletes User reference for associated taskId
	 * 
	 * @param taskId
	 * @throws UserCreationException
	 * @throws UserNotFoundException
	 */
	private void deleteUserTaskIdReferences(int taskId) throws UserCreationException, UserNotFoundException {
		// Update projectId to 0 for any user
		List<UserDetails> userDetailsList = userService.findAllUsers();
		for (UserDetails userDetails : userDetailsList) {
			if ((null != userDetails.getTask()) && (userDetails.getTask().getTaskId() == taskId)) {
				userDetails.setTask(null);
				userService.updateUser(userDetails);
			}
		}
	}

	@Override
	public void deleteTaskByTaskId(int taskId)
			throws TaskNotFoundException, UserCreationException, UserNotFoundException {
		// find task via taskId
		findTaskDetailsById(taskId);
		// deletes Task if matching taskId found in DB
		taskRepository.deleteById(taskId);
		// nullify taskId references for taskId in users table
		deleteUserTaskIdReferences(taskId);
	}
}