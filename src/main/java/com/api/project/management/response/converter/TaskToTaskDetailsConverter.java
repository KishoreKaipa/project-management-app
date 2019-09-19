/**
 * 
 */
package com.api.project.management.response.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.api.project.management.jpa.model.Task;
import com.api.project.management.model.TaskDetails;

/**
 * Task To TaskDetails Response Converter
 * 
 * @author Narasimha Kishore Kaipa
 *
 */
@Component
public class TaskToTaskDetailsConverter implements Converter<Task, TaskDetails> {
	@Override
	public TaskDetails convert(Task taskData) {
		TaskDetails taskDetails = new TaskDetails();
		taskDetails.setTaskId(taskData.getTaskId());
		taskDetails.setParentTask(taskData.getParentTask());
		taskDetails.setStartDate(taskData.getStartDate());
		taskDetails.setEndDate(taskData.getEndDate());
		taskDetails.setProject(taskData.getProject());
		taskDetails.setPriority(taskData.getPriority());
		taskDetails.setStatus(taskData.getStatus());
		return taskDetails;
	}
}