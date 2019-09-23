/**
 * 
 */
package com.api.project.management.request.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.api.project.management.jpa.model.Task;
import com.api.project.management.model.TaskDetails;
import com.api.project.management.util.RequestConversionUtils;

/**
 * TaskDetails to Task Request Converter
 * 
 * @author Narasimha Kishore Kaipa
 *
 */
@Component
public class TaskDetailsToTaskConverter implements Converter<TaskDetails, Task> {

	@Autowired
	RequestConversionUtils requestConversionUtils;

	@Override
	public Task convert(TaskDetails taskDetails) {
		return requestConversionUtils.populateTaskDataFromTaskDetails(taskDetails, true, true);
	}
}
