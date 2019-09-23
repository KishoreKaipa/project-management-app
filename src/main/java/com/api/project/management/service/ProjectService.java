/**
 * 
 */
package com.api.project.management.service;

import java.util.List;

import com.api.project.management.exception.ProjectCreationException;
import com.api.project.management.exception.ProjectNotFoundException;
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
	 * @throws UserNotFoundException
	 * @throws ProjectCreationException
	 */
	ProjectDetails createProject(ProjectDetails projectRequest) throws UserNotFoundException, ProjectCreationException;

	/**
	 * Finds Project by projectId
	 * 
	 * @param projectId
	 * @return
	 * @throws ProjectNotFoundException
	 */
	ProjectDetails findProjectByProjectId(int projectId) throws ProjectNotFoundException;

	/**
	 * Lists all Projects
	 * 
	 * @return projectDetailsList
	 */
	List<ProjectDetails> findAllProjects();

	/**
	 * Updates Project Details
	 * 
	 * @param projectRequest
	 * @return projectResponse
	 * @throws ProjectNotFoundException
	 * @throws UserNotFoundException
	 */
	ProjectDetails updateProjectDetails(ProjectDetails projectRequest)
			throws ProjectNotFoundException, UserNotFoundException;

	/**
	 * Deletes Project
	 * 
	 * @param projectRequest
	 * @throws ProjectNotFoundException
	 */
	void deleteProject(ProjectDetails projectRequest) throws ProjectNotFoundException;

	/**
	 * Deletes Project by projectId
	 * 
	 * @param projectId
	 * @throws ProjectNotFoundException
	 */
	void deleteProjectById(int projectId) throws ProjectNotFoundException;
}
