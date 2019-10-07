package com.zs.admin.configure;


import com.zs.admin.web.interceptor.AuthorityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author handy
 * @Description: {mvc配置拦截器}
 * @date 2019/9/2 18:13
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private AuthorityInterceptor authorityInterceptor;


    /**
     * 添加拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorityInterceptor).addPathPatterns("/**").excludePathPatterns(getExcludePath());
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
        List<String> patterns = new ArrayList<>();
        patterns.add("/");
        patterns.add("/login");
        patterns.add("/logout");
        patterns.add("/register");
        patterns.add("/accountIsExist/**");
        patterns.add("/page/404");

        //静态资源
        patterns.add("/lib/**");
        patterns.add("/js/**");
        patterns.add("/images/**");
        patterns.add("/css/**");
        patterns.add("/diagram-viewer/**");
        patterns.add("/editor-app/**");
        patterns.add("/modeler.html");
        return patterns;
    }
}
