/**
 * 
 */
package com.api.project.management.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.api.project.management.jpa.model.Project;

/**
 * Project JPA Repository for Project Management App
 *  @author Narasimha Kishore Kaipa
 *
 */
@Repository
public interface ProjectRepository extends CrudRepository<Project, Integer> {

}
