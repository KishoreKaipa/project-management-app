/**
 * 
 */
package com.api.project.management.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.project.management.exception.ParentTaskCreationException;
import com.api.project.management.exception.ParentTaskNotFoundException;
import com.api.project.management.exception.ProjectNotFoundException;
import com.api.project.management.exception.TaskCreationException;
import com.api.project.management.exception.TaskNotFoundException;
import com.api.project.management.exception.UserCreationException;
import com.api.project.management.exception.UserNotFoundException;
import com.api.project.management.model.ParentTaskDetails;
import com.api.project.management.service.ParentTaskService;

import lombok.extern.slf4j.Slf4j;

/**
 * Tasks module controller
 * 
 * @author Narasimha Kishore Kaipa
 *
 */
@RestController
@RequestMapping(path = "/parent/tasks")
@Slf4j
public class ParentTaskResource {

	@Autowired
	ParentTaskService parentTaskService;

	/**
	 * Creates Parent Task
	 * 
	 * @param parentTaskDetailsRequest
	 * @return parentTaskDetailsResponse
	 * @throws ProjectNotFoundException
	 * @throws ParentTaskCreationException
	 */
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ParentTaskDetails> createParentTask(@RequestBody ParentTaskDetails parentTaskDetailsRequest)
			throws ProjectNotFoundException, ParentTaskCreationException {
		log.info("Create Parent Task request received: " + parentTaskDetailsRequest);
		return (ResponseEntity<ParentTaskDetails>) ResponseEntity
				.ok(parentTaskService.createParentTask(parentTaskDetailsRequest));
	}

	/**
	 * Lists all parent tasks
	 * 
	 * @return list of parentTaskDetails
	 * @throws ProjectNotFoundException
	 */
	@GetMapping
	public ResponseEntity<List<ParentTaskDetails>> findAllParentTasks() throws ProjectNotFoundException {
		log.info("Find all parent tasks request received: ");
		return ResponseEntity.ok(parentTaskService.findAllParentTaskDetails());
	}

	/**
	 * Finds ParentTask by parentTaskId
	 * 
	 * @param parentTaskId
	 * @return parentTaskDetails
	 * @throws ParentTaskNotFoundException
	 * @throws ProjectNotFoundException
	 */
	@GetMapping(path = "/{parentTaskId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ParentTaskDetails> findParentTaskByTaskId(@PathVariable Integer parentTaskId)
			throws ParentTaskNotFoundException, ProjectNotFoundException {
		log.info("Find by parentTaskId " + parentTaskId + " request received: ");
		return ResponseEntity.ok(parentTaskService.findParentTaskDetailsById(parentTaskId));
	}

	/**
	 * Updates Parent Task
	 * 
	 * @param parentTaskRequest
	 * @return parentTaskResponse
	 * @throws ProjectNotFoundException
	 * @throws ParentTaskCreationException
	 * @throws ParentTaskNotFoundException
	 */
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ParentTaskDetails> updateParentTaskDetails(@RequestBody ParentTaskDetails parentTaskRequest)
			throws ProjectNotFoundException, ParentTaskCreationException, ParentTaskNotFoundException {
		log.info("Update Parent Task Details request received: " + parentTaskRequest);
		return (ResponseEntity<ParentTaskDetails>) ResponseEntity
				.ok(parentTaskService.updateParentTask(parentTaskRequest));
	}

	/**
	 * Deletes Parent Task
	 * 
	 * @param parentTaskDetailsRequest
	 * @return deleted parentTaskId
	 * @throws ParentTaskNotFoundException
	 * @throws UserCreationException
	 * @throws UserNotFoundException
	 * @throws TaskCreationException
	 * @throws TaskNotFoundException
	 * @throws ProjectNotFoundException
	 */
	@DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> deleteParentTask(@RequestBody ParentTaskDetails parentTaskDetailsRequest)
			throws ParentTaskNotFoundException, UserCreationException, UserNotFoundException, TaskCreationException,
			TaskNotFoundException, ProjectNotFoundException {
		log.info("Delete Parent Task request received: " + parentTaskDetailsRequest);
		parentTaskService.deleteParentTask(parentTaskDetailsRequest);
		return ResponseEntity.ok(parentTaskDetailsRequest.getParentId());
	}

	/**
	 * Deletes Parent Task by parentTaskId
	 * 
	 * @param parentTaskId
	 * @return deleted parentTaskId
	 * @throws ParentTaskNotFoundException
	 * @throws UserCreationException
	 * @throws UserNotFoundException
	 * @throws TaskCreationException
	 * @throws TaskNotFoundException
	 * @throws ProjectNotFoundException
	 */
	@DeleteMapping(path = "/{parentTaskId}")
	public ResponseEntity<Integer> deleteParentTaskByTaskId(@PathVariable Integer parentTaskId)
			throws ParentTaskNotFoundException, UserCreationException, UserNotFoundException, TaskCreationException,
			TaskNotFoundException, ProjectNotFoundException {
		log.info("Delete Parent Task request received for parentTaskId: " + parentTaskId);
		parentTaskService.deleteParentTaskByTaskId(parentTaskId);
		return ResponseEntity.ok(parentTaskId);
	}
}