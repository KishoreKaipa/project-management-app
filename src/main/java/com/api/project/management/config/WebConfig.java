/**
 * 
 */
package com.api.project.management.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.api.project.management.request.converter.ParentTaskDetailsToParentTaskConverter;
import com.api.project.management.request.converter.ProjectDetailsToProjectConverter;
import com.api.project.management.request.converter.TaskDetailsToTaskConverter;
import com.api.project.management.request.converter.UserDetailsToUserConverter;
import com.api.project.management.response.converter.ParentTaskToParentTaskDetailsConverter;
import com.api.project.management.response.converter.ProjectToProjectDetailsConverter;
import com.api.project.management.response.converter.TaskToTaskDetailsConverter;
import com.api.project.management.response.converter.UserToUserDetailsConverter;

/**
 * Registration of Spring Converter services
 * 
 * @author Narasimha Kishore Kaipa
 *
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addFormatters(FormatterRegistry registry) { 
		
		// Request Converters
		registry.addConverter(new UserDetailsToUserConverter());
		registry.addConverter(new ProjectDetailsToProjectConverter());
		registry.addConverter(new TaskDetailsToTaskConverter());
		registry.addConverter(new ParentTaskDetailsToParentTaskConverter());
		
		// Response Converters
		registry.addConverter(new UserToUserDetailsConverter());
		registry.addConverter(new ProjectToProjectDetailsConverter());
		registry.addConverter(new TaskToTaskDetailsConverter());
		registry.addConverter(new ParentTaskToParentTaskDetailsConverter());
	}
}
