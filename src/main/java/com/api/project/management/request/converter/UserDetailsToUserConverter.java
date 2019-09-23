/**
 * 
 */
package com.api.project.management.request.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.api.project.management.jpa.model.User;
import com.api.project.management.model.UserDetails;
import com.api.project.management.util.RequestConversionUtils;

/**
 * UserDetails to User Request Converter
 * 
 * @author Narasimha Kishore Kaipa
 *
 */
@Component
public class UserDetailsToUserConverter implements Converter<UserDetails, User> {

	@Autowired
	RequestConversionUtils requestConversionUtils;

	@Override
	public User convert(UserDetails userDetails) {
		User userData = new User();
		if (userDetails.getUserId() > 0) {
			userData.setUserId(userDetails.getUserId());
		}
		userData.setFirstName(userDetails.getFirstName());
		userData.setLastName(userDetails.getLastName());
		userData.setEmployeeId(userDetails.getEmployeeId());
		if (null != userDetails.getProjectDetails()) {
			userData.setProject(
					requestConversionUtils.populateProjectDataFromProjectDetails(userDetails.getProjectDetails()));
		}
		if (null != userDetails.getTaskDetails()) {
			userData.setTask(
					requestConversionUtils.populateTaskDataFromTaskDetails(userDetails.getTaskDetails(), false, false));
		}
		return userData;
	}
}