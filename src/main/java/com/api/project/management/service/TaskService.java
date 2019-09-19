/**
 * 
 */
package com.api.project.management.service;

import java.util.List;

import com.api.project.management.exception.TaskCreationException;
import com.api.project.management.exception.TaskNotFoundException;
import com.api.project.management.exception.UserCreationException;
import com.api.project.management.exception.UserNotFoundException;
import com.api.project.management.model.TaskDetails;

/**
 * Task Service Interface
 * 
 * @author Narasimha Kishore Kaipa
 *
 */
public interface TaskService {

	/**
	 * Creates Task
	 * 
	 * @param taskDetailsRequest
	 * @return taskDetailsResponse
	 * @throws UserCreationException
	 * @throws UserNotFoundException
	 * @throws TaskCreationException
	 */
	TaskDetails createTask(TaskDetails taskDetailsRequest)
			throws UserCreationException, UserNotFoundException, TaskCreationException;

	/**
	 * Updates Task
	 * 
	 * @param taskDetailsRequest
	 * @return taskDetailsResponse
	 * @throws TaskCreationException
	 * @throws UserCreationException
	 * @throws UserNotFoundException
	 * @throws TaskNotFoundException
	 */
	TaskDetails updateTask(TaskDetails taskDetailsRequest)
			throws TaskCreationException, UserCreationException, UserNotFoundException, TaskNotFoundException;

	/**
	 * Finds Task by taskId
	 * 
	 * @param taskId
	 * @return
	 * @throws TaskNotFoundException
	 */
	TaskDetails findTaskDetailsById(int taskId) throws TaskNotFoundException;

	/**
	 * finds all taskDetails
	 * 
	 * @return
	 */
	List<TaskDetails> findAllTaskDetails();

	/**
	 * Deletes Task
	 * 
	 * @param taskDetails
	 * @throws TaskNotFoundException
	 * @throws UserCreationException
	 * @throws UserNotFoundException
	 */
	void deleteTask(TaskDetails taskDetails) throws TaskNotFoundException, UserCreationException, UserNotFoundException;

	/**
	 * Deletes Task by taskId
	 * 
	 * @param taskId
	 * @throws TaskNotFoundException
	 * @throws UserCreationException
	 * @throws UserNotFoundException
	 */
	void deleteTaskByTaskId(int taskId) throws TaskNotFoundException, UserCreationException, UserNotFoundException;
}
