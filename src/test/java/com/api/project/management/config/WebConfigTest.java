/**
 * 
 */
package com.api.project.management.config;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.format.FormatterRegistry;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Narasimha Kishore Kaipa
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class WebConfigTest {

	@InjectMocks
	WebConfig webConfig;

	@Mock
	FormatterRegistry formatterRegistry;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testWebConfig() {
		webConfig.addFormatters(formatterRegistry);
	}
}
