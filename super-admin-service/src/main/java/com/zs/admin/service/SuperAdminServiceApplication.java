package com.zs.admin.service;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(scanBasePackages = {"com.zs"},
		exclude = {SecurityAutoConfiguration.class})
@ImportResource("classpath:applicationContext-base.xml")
@MapperScan("com/zs/admin/service/mapper")
public class SuperAdminServiceApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(SuperAdminServiceApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SuperAdminServiceApplication.class);
	}
}
