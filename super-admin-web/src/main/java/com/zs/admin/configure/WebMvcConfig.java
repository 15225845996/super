package com.zs.admin.configure;


import cn.hutool.core.collection.CollUtil;
import com.zs.admin.web.interceptor.AuthorityInterceptor;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author handy
 * @Description: {mvc配置拦截器}
 * @date 2019/9/2 18:13
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${allow.urls}")
    private String allowUrls;

    @Autowired
    private AuthorityInterceptor authorityInterceptor;


    /**
     * 添加拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(authorityInterceptor).addPathPatterns("/**");
        List<String> excludePath = getExcludePath();
        if(CollUtil.isNotEmpty(excludePath)){
            interceptorRegistration.excludePathPatterns(excludePath);
        }
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
    }

    /**
     * 排除路径
     *
     * @return
     */
    private List<String> getExcludePath() {
        if (StringUtils.isNotBlank(allowUrls)) {
            return Arrays.asList(allowUrls.split(","));
        }
        return null;
    }
}
