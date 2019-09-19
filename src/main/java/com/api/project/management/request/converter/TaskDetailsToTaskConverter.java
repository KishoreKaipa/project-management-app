/**
 * 
 */
package com.api.project.management.request.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.api.project.management.jpa.model.Task;
import com.api.project.management.model.TaskDetails;

/**
 * TaskDetails to Task Request Converter
 * 
 * @author Narasimha Kishore Kaipa
 *
 */
@Component
public class TaskDetailsToTaskConverter implements Converter<TaskDetails, Task> {

	@Override
	public Task convert(TaskDetails taskDetails) {
		Task taskData = new Task();
		if (taskDetails.getTaskId() > 0) {
			taskData.setTaskId(taskDetails.getTaskId());
		}
		taskData.setParentTask(taskDetails.getParentTask());
		taskData.setProject(taskDetails.getProject());
		taskData.setTaskDescription(taskDetails.getTaskDescription());
		taskData.setStatus(taskDetails.getStatus());
		taskData.setPriority(taskDetails.getPriority());
		taskData.setStartDate(taskDetails.getStartDate());
		taskData.setEndDate(taskDetails.getEndDate());
		return taskData;
	}
}
