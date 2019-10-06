package com.zs.admin.web.interceptor;

import com.zs.admin.api.constant.Constant;
import io.swagger.models.HttpMethod;
import lombok.val;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * @Auther: zs
 * @Date: 2019/10/6 09:44
 * @Description:权限校验
 */
@Component
public class AuthorityInterceptor implements HandlerInterceptor{
    private static final String API_PATH_PREFIX = "/api";

    private static final String[] PUBLIC_HREF = new String[]{"/page/index","/page/home"};

    /**
     * 前置拦截
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //根据 URL 规则做不容类型请求的处理
        String url = request.getRequestURI();
        //根据用户session校验权限
        boolean flag = false;
        val userVo = request.getSession().getAttribute(Constant.USER_INFO_KEY);
        if (userVo != null) {
            List<String> urls = request.getSession().getAttribute(Constant.USER_SOURCES_HREF) == null?
                    null:(List<String>)request.getSession().getAttribute(Constant.USER_SOURCES_HREF);
            if(Arrays.asList(PUBLIC_HREF).contains(url) || url.startsWith(API_PATH_PREFIX) || (urls != null && urls.contains(url))){
                flag = true;
            }else{
                flag = false;
            }
        } else {
            response.sendRedirect("/");
        }
        return flag;
    }

    /**
     * 在业务处理器处理请求完成之后，生成视图之前执行
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

    /**
     * 在DispatcherServlet完全处理完请求之后被调用，可用于清理资源
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

    }
}
