package com.zs.admin.service;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(scanBasePackages = {"com.zs"},
		exclude = {SecurityAutoConfiguration.class})
@ImportResource("classpath:applicationContext-base.xml")
@MapperScan("com/zs/admin/service/mapper")
public class ServiceApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(ServiceApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ServiceApplication.class);
	}

	/**
	 * 分页插件
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
		// 你的最大单页限制数量，默认 500 条，小于 0 如 -1 不受限制
		paginationInterceptor.setLimit(100);
		return paginationInterceptor;
	}
}
