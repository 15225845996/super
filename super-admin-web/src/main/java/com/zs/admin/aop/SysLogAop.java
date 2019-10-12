package com.zs.admin.aop;


import cn.hutool.json.JSONUtil;
import com.zs.admin.annotation.SysLog;
import com.zs.admin.api.entry.SysLogInfo;
import com.zs.admin.api.service.sys.ISysLogInfoService;
import com.zs.admin.api.service.sys.ISysLogService;
import com.zs.admin.api.vo.ResultVo;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Auther: zs
 * @Date: 2019/10/11 09:25
 * @Description:
 */
@Aspect
@Component
public class SysLogAop {
    Logger logger = LoggerFactory.getLogger(SysLogAop.class);

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ISysLogService logService;
    @Autowired
    private ISysLogInfoService logInfoService;

    /**
     * 注解切点层切点
     */
    @Pointcut("@annotation(com.zs.admin.annotation.SysLog)")
    public void sysLog(){

    }

    /**
     * 环绕通知
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("sysLog()")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("环绕通知：前");
        Object obj = joinPoint.proceed();
        logger.info("环绕通知：后");

        List<SysLogInfo> logInfos = new ArrayList<>();
        SysLogInfo logInfo = new SysLogInfo();
        logInfo.setIsParam(false);
        if(obj instanceof ResultVo){
            logInfo.setValue(JSONUtil.parse(obj).toString());
        }else{
            logInfo.setValue(obj.toString());
        }
        logInfos.add(logInfo);

        SysLog logEnum = getAnnotation(joinPoint);
        Map<String, String[]> map = request.getParameterMap();
        String url = request.getRequestURI();
        String retUrl = request.getHeader("Referer");
        String method = request.getMethod();
        if(map != null){
            for (Map.Entry<String, String[]> entry : map.entrySet()) {
                logInfo = new SysLogInfo();
                logInfo.setIsParam(true);
                logInfo.setKey(entry.getKey());
                if(entry.getValue().length == 1){
                    logInfo.setValue(entry.getValue()[0].toString());
                }else{
                    logInfo.setValue(JSONUtil.parse(entry.getValue()).toString());
                }
                logInfos.add(logInfo);
            }
        }
        com.zs.admin.api.entry.SysLog sysLog = new com.zs.admin.api.entry.SysLog();
        sysLog.setUrl(url);
        sysLog.setProviousUrl(retUrl);
        sysLog.setMethod(method);
        sysLog.setCategoryId(logEnum.category().getCategoryId());
        sysLog.setCategoryName(logEnum.category().getCategoryName());
        sysLog.setTypeId(logEnum.type().getTypeId());
        sysLog.setTypeName(logEnum.type().getTypeName());
        sysLog.setDescr(logEnum.desc());
        sysLog.setIp(getIp(request));
        sysLog.setLogInfos(logInfos);
        boolean save = logService.save2(sysLog);
        return obj;
    }


    /**
     * 后置最终通知（目标方法只要执行完了就会执行后置通知方法）
     * @param joinPoint
     */
    @After("sysLog()")
    public void doAfterAdvice(JoinPoint joinPoint){
    }


    /**
     * 获取注解信息
     *
     * @param joinPoint 切点
     * @return discription
     */
    public static SysLog getAnnotation(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SysLog annotation = method
                .getAnnotation(SysLog.class);
        return annotation;
    }


    protected String getIp(HttpServletRequest req){
        String Xip = req.getHeader("X-Real-IP");
        String XFor = req.getHeader("X-Forwarded-For");
        if(StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = XFor.indexOf(",");
            if(index != -1){
                return XFor.substring(0,index);
            }else{
                return XFor;
            }
        }
        XFor = Xip;
        if(StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
            return XFor;
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = req.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = req.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = req.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = req.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = req.getRemoteAddr();
        }
        if(XFor != null && XFor.length() > 50){//ip地址最长50
            XFor = XFor.substring(0,50);
        }
        return XFor;
    }
}
