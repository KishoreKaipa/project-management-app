/**
 * 
 */
package com.api.project.management.response.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.api.project.management.jpa.model.Task;
import com.api.project.management.model.TaskDetails;
import com.api.project.management.util.ResponseConversionUtils;

/**
 * Task To TaskDetails Response Converter
 * 
 * @author Narasimha Kishore Kaipa
 *
 */
@Component
public class TaskToTaskDetailsConverter implements Converter<Task, TaskDetails> {
	
	@Autowired
	ResponseConversionUtils responseConversionUtils;
	
	@Override
	public TaskDetails convert(Task taskData) {
		return responseConversionUtils.populateTaskDetailsFromTaskData(taskData, true, true, true);
	}
}