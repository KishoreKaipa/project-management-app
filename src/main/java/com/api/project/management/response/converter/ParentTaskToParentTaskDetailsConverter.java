/**
 * 
 */
package com.api.project.management.response.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.api.project.management.jpa.model.ParentTask;
import com.api.project.management.model.ParentTaskDetails;
import com.api.project.management.util.ResponseConversionUtils;

/**
 * ParentTask to ParentTaskDetails Response Converter
 * 
 * @author Narasimha Kishore Kaipa
 *
 */
@Component
public class ParentTaskToParentTaskDetailsConverter implements Converter<ParentTask, ParentTaskDetails> {

	@Autowired
	ResponseConversionUtils responseConversionUtils;

	@Override
	public ParentTaskDetails convert(ParentTask parentTaskData) {
		return responseConversionUtils.populateParentTaskDetailsFromParentTaskData(parentTaskData, true);
	}
}
