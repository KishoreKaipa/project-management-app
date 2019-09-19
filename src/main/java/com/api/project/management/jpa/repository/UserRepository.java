
package com.api.project.management.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.api.project.management.jpa.model.User;

/**
 * User JPA Repository for Project Management App
 * @author Narasimha Kishore Kaipa
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

}
