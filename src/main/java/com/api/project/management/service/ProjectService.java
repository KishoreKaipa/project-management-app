/**
 * 
 */
package com.api.project.management.service;

import java.util.List;

import com.api.project.management.exception.ProjectCreationException;
import com.api.project.management.exception.ProjectNotFoundException;
import com.api.project.management.exception.UserCreationException;
import com.api.project.management.exception.UserNotFoundException;
import com.api.project.management.model.ProjectDetails;

/**
 * Project Service Interface
 * 
 * @author Narasimha Kishore Kaipa
 */
public interface ProjectService {

	/**
	 * Creates Project
	 * 
	 * @param projectRequest
	 * @return projectResponse
	 * @throws UserCreationException
	 * @throws UserNotFoundException
	 * @throws ProjectCreationException
	 */
	ProjectDetails createProject(ProjectDetails projectRequest)
			throws UserCreationException, UserNotFoundException, ProjectCreationException;

	/**
	 * Finds Project by projectId
	 * 
	 * @param projectId
	 * @return projectDetails
	 * @throws ProjectNotFoundException
	 */
	ProjectDetails findProjectByProjectId(int projectId) throws ProjectNotFoundException;

	/**
	 * Lists all Projects
	 * 
	 * @return
	 */
	List<ProjectDetails> findAllProjects();

	/**
	 * Updates Project Details
	 * 
	 * @param projectRequest
	 * @return projectResponse
	 * @throws ProjectNotFoundException
	 * @throws ProjectCreationException
	 * @throws UserNotFoundException
	 * @throws UserCreationException
	 */
	ProjectDetails updateProjectDetails(ProjectDetails projectRequest)
			throws ProjectNotFoundException, ProjectCreationException, UserCreationException, UserNotFoundException;

	/**
	 * Deletes Project
	 * 
	 * @param projectRequest
	 * @throws ProjectNotFoundException
	 * @throws UserCreationException
	 * @throws UserNotFoundException
	 */
	void deleteProject(ProjectDetails projectRequest)
			throws ProjectNotFoundException, UserCreationException, UserNotFoundException;

	/**
	 * Deletes Project by projectId
	 * 
	 * @param projectId
	 * @throws ProjectNotFoundException
	 * @throws UserCreationException
	 * @throws UserNotFoundException
	 */
	void deleteProjectById(int projectId) throws ProjectNotFoundException, UserCreationException, UserNotFoundException;
}
