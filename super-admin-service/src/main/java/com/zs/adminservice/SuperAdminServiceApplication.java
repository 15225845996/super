package com.zs.adminservice;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = {"com.zs"},
		exclude = {SecurityAutoConfiguration.class})
public class SuperAdminServiceApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(SuperAdminServiceApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SuperAdminServiceApplication.class);
	}
}
