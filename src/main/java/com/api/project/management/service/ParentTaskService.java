/**
 * 
 */
package com.api.project.management.service;

import java.util.List;

import com.api.project.management.exception.ParentTaskCreationException;
import com.api.project.management.exception.ParentTaskNotFoundException;
import com.api.project.management.exception.ProjectNotFoundException;
import com.api.project.management.exception.TaskCreationException;
import com.api.project.management.exception.TaskNotFoundException;
import com.api.project.management.exception.UserCreationException;
import com.api.project.management.exception.UserNotFoundException;
import com.api.project.management.model.ParentTaskDetails;

/**
 * ParentTask Service Interface
 * 
 * @author Narasimha Kishore Kaipa
 *
 */
public interface ParentTaskService {

	/**
	 * Creates Parent Task
	 * 
	 * @param parentTaskDetailsRequest
	 * @return parentTaskDetailsResponse
	 * @throws ProjectNotFoundException
	 * @throws ParentTaskCreationException
	 */
	ParentTaskDetails createParentTask(ParentTaskDetails parentTaskDetailsRequest)
			throws ProjectNotFoundException, ParentTaskCreationException;

	/**
	 * Updates Parent Task
	 * 
	 * @param parentTaskDetailsRequest
	 * @return parentTaskDetailsResponse
	 * @throws ProjectNotFoundException
	 * @throws ParentTaskCreationException
	 * @throws ParentTaskNotFoundException
	 */
	ParentTaskDetails updateParentTask(ParentTaskDetails parentTaskDetailsRequest)
			throws ProjectNotFoundException, ParentTaskCreationException, ParentTaskNotFoundException;

	/**
	 * Finds ParentTask by parentTaskId
	 * 
	 * @param parentTaskId
	 * @return parentTaskDetails
	 * @throws ParentTaskNotFoundException
	 * @throws ProjectNotFoundException
	 */
	ParentTaskDetails findParentTaskDetailsById(int parentTaskId)
			throws ParentTaskNotFoundException, ProjectNotFoundException;

	/**
	 * Finds all ParentTask details
	 * 
	 * @return parentTaskDetails list
	 * @throws ProjectNotFoundException
	 */
	List<ParentTaskDetails> findAllParentTaskDetails() throws ProjectNotFoundException;

	/**
	 * Deletes Parent Task
	 * 
	 * @param parentTaskDetailsRequest
	 * @throws ParentTaskNotFoundException
	 * @throws UserCreationException
	 * @throws UserNotFoundException
	 * @throws TaskCreationException
	 * @throws TaskNotFoundException
	 * @throws ProjectNotFoundException
	 */
	void deleteParentTask(ParentTaskDetails parentTaskDetailsRequest)
			throws ParentTaskNotFoundException, UserCreationException, UserNotFoundException, TaskCreationException,
			TaskNotFoundException, ProjectNotFoundException;

	/**
	 * Deletes Parent Task by parentTaskId
	 * 
	 * @param parentTaskId
	 * @throws ParentTaskNotFoundException
	 * @throws UserCreationException
	 * @throws UserNotFoundException
	 * @throws TaskCreationException
	 * @throws TaskNotFoundException
	 * @throws ProjectNotFoundException
	 */
	void deleteParentTaskByTaskId(int parentTaskId) throws ParentTaskNotFoundException, UserCreationException,
			UserNotFoundException, TaskCreationException, TaskNotFoundException, ProjectNotFoundException;
}
