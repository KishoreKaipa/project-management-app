/**
 * 
 */
package com.api.project.management.response.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.api.project.management.jpa.model.User;
import com.api.project.management.model.UserDetails;
import com.api.project.management.util.ResponseConversionUtils;

/**
 * User to UserDetails Response Converter
 * 
 * @author Narasimha Kishore Kaipa
 *
 */
@Component
public class UserToUserDetailsConverter implements Converter<User, UserDetails> {

	@Autowired
	ResponseConversionUtils responseConversionUtils;

	@Override
	public UserDetails convert(User userData) {
		return responseConversionUtils.populateUserDetailsFromUserData(userData, true, true);
	}
}
