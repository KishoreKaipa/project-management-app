/**
 * 
 */
package com.api.project.management.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.project.management.jpa.model.ParentTask;

/**
 * Parent Task JPA Repository for Project Management App
 *  @author Narasimha Kishore Kaipa
 *
 */
@Repository
public interface ParentTaskRepository extends JpaRepository<ParentTask, Integer> {
}
