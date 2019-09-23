/**
 * 
 */
package com.api.project.management.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.project.management.exception.UserCreationException;
import com.api.project.management.exception.UserNotFoundException;
import com.api.project.management.model.UserDetails;
import com.api.project.management.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * Users module controller
 * 
 * @author Narasimha Kishore Kaipa
 *
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(path = "/users")
@Slf4j
public class UserResource {

	@Autowired
	UserService userService;

	/**
	 * Creates User
	 * 
	 * @param userRequest
	 * @return userDetails
	 * @throws UserCreationException
	 */
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDetails> createUser(@RequestBody UserDetails userRequest) throws UserCreationException {
		log.info("Create User request received: " + userRequest);
		return (ResponseEntity<UserDetails>) ResponseEntity.ok(userService.createUser(userRequest));
	}

	/**
	 * find all users
	 * 
	 * @return list of userDetails
	 */
	@GetMapping
	public ResponseEntity<List<UserDetails>> findAllUsers() {
		log.info("Find all Users request received: ");
		return ResponseEntity.ok(userService.findAllUsers());
	}

	/**
	 * finds user by userId
	 * 
	 * @param userId
	 * @return userDetails
	 * @throws UserNotFoundException
	 */
	@GetMapping(path = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDetails> findUserByUserId(@PathVariable Integer userId) throws UserNotFoundException {
		log.info("Find by userId " + userId + " request received: ");
		return ResponseEntity.ok(userService.findUserByUserId(userId));
	}

	/**
	 * Updates user details
	 * 
	 * @param userRequest
	 * @return userDetails
	 * @throws UserCreationException
	 * @throws UserNotFoundException
	 */
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDetails> updateUser(@RequestBody UserDetails userRequest)
			throws UserCreationException, UserNotFoundException {
		log.info("Update User request received: " + userRequest);
		return (ResponseEntity<UserDetails>) ResponseEntity.ok(userService.updateUser(userRequest));
	}

	/**
	 * Deletes user by body
	 * 
	 * @param userRequest
	 * @return userId deleted
	 * @throws UserNotFoundException
	 */
	@DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> deleteUser(@RequestBody UserDetails userRequest) throws UserNotFoundException {
		log.info("Delete User request received: " + userRequest);
		userService.deleteUser(userRequest);
		return ResponseEntity.ok(userRequest.getUserId());
	}

	/**
	 * Deletes user by userId
	 * 
	 * @param userId
	 * @return userId
	 * @throws UserNotFoundException
	 */
	@DeleteMapping(path = "/{userId}")
	public ResponseEntity<Integer> deleteUserByUserId(@PathVariable Integer userId) throws UserNotFoundException {
		log.info("Delete User request received for userId: " + userId);
		userService.deleteUserById(userId);
		return ResponseEntity.ok(userId);
	}
}
