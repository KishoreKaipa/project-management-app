/**
 * 
 */
package com.api.project.management.response.converter;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.api.project.management.jpa.model.Task;
import com.api.project.management.model.TaskDetails;
import com.api.project.management.util.DateUtils;

/**
 * Task To TaskDetails Response Converter
 * 
 * @author Narasimha Kishore Kaipa
 *
 */
@Component
public class TaskToTaskDetailsConverter implements Converter<Task, TaskDetails> {
	
	@Autowired
	DateUtils dateUtils;
	
	@Override
	public TaskDetails convert(Task taskData) {
		TaskDetails taskDetails = new TaskDetails();
		taskDetails.setTaskId(taskData.getTaskId());
		taskDetails.setParentTask(taskData.getParentTask());
		try {
			taskDetails.setStartDate(dateUtils.getDateWithoutTimestamp(taskData.getStartDate()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			taskDetails.setEndDate(dateUtils.getDateWithoutTimestamp(taskData.getEndDate()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		taskDetails.setProject(taskData.getProject());
		taskDetails.setPriority(taskData.getPriority());
		taskDetails.setStatus(taskData.getStatus());
		taskDetails.setTaskDescription(taskData.getTaskDescription());
		return taskDetails;
	}
}