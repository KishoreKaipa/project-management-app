/**
 * 
 */
package com.api.project.management.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.api.project.management.exception.UserCreationException;
import com.api.project.management.exception.UserNotFoundException;
import com.api.project.management.jpa.model.User;
import com.api.project.management.jpa.repository.UserRepository;
import com.api.project.management.model.UserDetails;
import com.api.project.management.request.converter.UserDetailsToUserConverter;
import com.api.project.management.response.converter.UserToUserDetailsConverter;
import com.api.project.management.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * User Service Implementation
 * 
 * @author Narasimha Kishore Kaipa
 *
 */
@Component
@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserDetailsToUserConverter userRequestConverter;

	@Autowired
	UserToUserDetailsConverter userResponseConverter;

	@Override
	public UserDetails createUser(UserDetails userDetailsRequest) throws UserCreationException {
		validateUserRequestData(userDetailsRequest);
		return userResponseConverter.convert(userRepository.save(userRequestConverter.convert(userDetailsRequest)));
	}

	/**
	 * validates new user data and throws error if there is validation error
	 * 
	 * @param userRequest
	 * @throws UserCreationException
	 */
	private void validateUserRequestData(UserDetails userRequest) throws UserCreationException {
		if (StringUtils.isBlank(userRequest.getFirstName())) {
			log.error("User Creation validation failed  firstName is blank");
			throw new UserCreationException("firstName");
		}
		if (StringUtils.isBlank(userRequest.getLastName())) {
			log.error("User Creation validation failed  lastName is blank");
			throw new UserCreationException("lastName");
		}
		if (userRequest.getEmployeeId() == 0) {
			log.error("User Creation validation failed  employeeId is 0");
			throw new UserCreationException("employeeId");
		}
	}

	@Override
	public UserDetails findUserByUserId(int userId) throws UserNotFoundException {
		Optional<User> userData = userRepository.findById(userId);
		if (!userData.isPresent()) {
			log.error("User with userId " + userId + " not found");
			throw new UserNotFoundException(userId);
		}
		return userResponseConverter.convert(userData.get());
	}

	@Override
	public UserDetails updateUser(UserDetails userDetailsRequest) throws UserCreationException, UserNotFoundException {
		// check if a user exists with requested userId before proceeding with update
		findUserByUserId(userDetailsRequest.getUserId());
		validateUserRequestData(userDetailsRequest);
		return userResponseConverter.convert(userRepository.save(userRequestConverter.convert(userDetailsRequest)));
	}

	@Override
	public List<UserDetails> findAllUsers() {
		List<UserDetails> userDetailsList = new ArrayList<UserDetails>();
		Iterable<User> userDataList = userRepository.findAll();
		for (User userData : userDataList) {
			userDetailsList.add(userResponseConverter.convert(userData));
		}
		return userDetailsList;
	}

	@Override
	public void deleteUser(UserDetails userRequest) throws UserNotFoundException {
		// check if a user exists with requested userId before deletion
		findUserByUserId(userRequest.getUserId());
		// delete user details upon verification
		userRepository.delete(userRequestConverter.convert(userRequest));
	}

	@Override
	public void deleteUserById(int userId) throws UserNotFoundException {
		// check if a user exists with requested userId before deletion
		findUserByUserId(userId);
		// delete user details upon succesful finding
		userRepository.deleteById(userId);
	}
}