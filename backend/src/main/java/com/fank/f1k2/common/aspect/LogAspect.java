package com.fank.f1k2.common.aspect;

import com.fank.f1k2.common.authentication.JWTUtil;
import com.fank.f1k2.common.properties.F1k2Properties;
import com.fank.f1k2.common.utils.HttpContextUtil;
import com.fank.f1k2.common.utils.IPUtil;
import com.fank.f1k2.system.domain.SysLog;
import com.fank.f1k2.system.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * AOP 记录用户操作日志
 *
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    @Autowired
    private F1k2Properties f1k2Properties;

    @Autowired
    private LogService logService;

    @Pointcut("@annotation(com.fank.f1k2.common.annotation.Log)")
    public void pointcut() {
        // do nothing
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result = null;
        long beginTime = System.currentTimeMillis();
        // 执行方法
        result = point.proceed();
        // 获取 request
        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
        // 设置 IP 地址
        String ip = IPUtil.getIpAddr(request);
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        if (f1k2Properties.isOpenAopLog()) {
            // 保存日志
            String token = (String) SecurityUtils.getSubject().getPrincipal();
            String username = "";
            if (StringUtils.isNotBlank(token)) {
                username = JWTUtil.getUsername(token);
            }

            SysLog log = new SysLog();
            log.setUsername(username);
            log.setIp(ip);
            log.setTime(time);
            logService.saveLog(point, log);
        }
        return result;
    }
}
