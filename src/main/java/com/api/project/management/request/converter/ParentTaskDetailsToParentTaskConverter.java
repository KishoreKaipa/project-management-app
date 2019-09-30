/**
 * 
 */
package com.api.project.management.request.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.api.project.management.jpa.model.ParentTask;
import com.api.project.management.model.ParentTaskDetails;
import com.api.project.management.util.RequestConversionUtils;

/**
 * ParentTaskDetails to ParentTask Request Converter
 * 
 * @author Narasimha Kishore Kaipa
 *
 */
@Component
public class ParentTaskDetailsToParentTaskConverter implements Converter<ParentTaskDetails, ParentTask> {

	@Autowired
	RequestConversionUtils requestConversionUtils;

	@Override
	public ParentTask convert(ParentTaskDetails parentTaskDetails) {
		return requestConversionUtils.populateParentTaskDataFromParentTaskDetails(parentTaskDetails, true);
	}

}
