/**
 * 
 */
package com.api.project.management.request.converter;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.api.project.management.jpa.model.Task;
import com.api.project.management.model.TaskDetails;
import com.api.project.management.util.DateUtils;

/**
 * TaskDetails to Task Request Converter
 * 
 * @author Narasimha Kishore Kaipa
 *
 */
@Component
public class TaskDetailsToTaskConverter implements Converter<TaskDetails, Task> {

	@Autowired
	DateUtils dateUtils;

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
		try {
			taskData.setStartDate(dateUtils.getDateWithoutTimestamp(taskDetails.getStartDate()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			taskData.setEndDate(dateUtils.getDateWithoutTimestamp(taskDetails.getEndDate()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return taskData;
	}
}
