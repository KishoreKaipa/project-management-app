package com.api.project.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * ProjectManagementApplication Spring Boot App
 *  @author Narasimha Kishore Kaipa
 *
 */
@SpringBootApplication
@ComponentScan
@EnableJpaAuditing
public class ProjectManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectManagementApplication.class, args);
	}
}
