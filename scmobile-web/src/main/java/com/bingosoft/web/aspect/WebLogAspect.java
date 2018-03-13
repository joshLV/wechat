package com.bingosoft.web.aspect;

import java.util.Arrays;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bingosoft.core.mongodb.service.IWebLogService;
import com.bingosoft.models.dto.UserInfoDto;
import com.bingosoft.models.mongodb.entities.WebLog;
import com.bingosoft.persist.mongodb.dao.IUserInfoRepository;
import com.bingosoft.utils.JSONUtils;
import com.bingosoft.utils.crypt.TokenUtils;


@Aspect
@Component
public class WebLogAspect {

	
	@Autowired
	IWebLogService webLogService;
	
	ThreadLocal<Long> startTime = new ThreadLocal<>();
	
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final Logger mongoLog = LoggerFactory.getLogger("MONGODB");
    
    
    @Pointcut("execution(public * com.bingosoft.web.controller.GoodsV2Controller.*(..))")

    public void webLog() {
    }


    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {

        // 接收到请求，记录请求内容
    	startTime.set(System.currentTimeMillis());
        //logger.info("WebLogAspect.doBefore()");

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = attributes.getRequest();


        // 记录下请求内容

//        logger.info("URL : " + request.getRequestURL().toString());
//
//        logger.info("HTTP_METHOD : " + request.getMethod());
//
//        logger.info("IP : " + request.getRemoteAddr());
//
//        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature
//                ().getName());
//
//        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
        WebLog log=new WebLog();
        log.setViewIp(request.getRemoteAddr());
        log.setUrl(request.getRequestURL().toString());
        logger.info(request.getRequestURL().toString());
        log.setArgs(Arrays.toString(joinPoint.getArgs()));
        UserInfoDto userDto = null;
		String token = request.getHeader("os");
		if (!StringUtils.isEmpty(token)) {
			String user = "";
			try {
				user = TokenUtils.decrypt(token);
			} catch (Exception e) {

			}
			if (!StringUtils.isEmpty(user))
				userDto = JSONUtils.toBean(user, UserInfoDto.class);

			if (userDto != null) {
				log.setUserName(userDto.getUserName());
				log.setOpenId(userDto.getOpenId());
				log.setPhoneNo(userDto.getPhoenNo());

			}
		}
		writeLogs(log);
        //获取所有参数方法一：
        //webLogService.writeWebLog(log);
       // mongoLog.info(JSONUtils.toJson(log));
       
       // Enumeration<String> enu = request.getParameterNames();

//        while (enu.hasMoreElements()) {
//
//            String paraName = enu.nextElement();
//
//            System.out.println(paraName + ": " + request.getParameter(paraName));
//
//        }
    }

    @AfterReturning("webLog()")
    public void doAfterReturning(JoinPoint joinPoint) {
    	logger.info("RESPONSE : " + joinPoint.toString());
		logger.info("SPEND TIME : " + (System.currentTimeMillis() - startTime.get()));
        // 处理完请求，返回内容
       // logger.info("WebLogAspect.doAfterReturning()");

    }
    
    @Async
    private void writeLogs(WebLog log)
    {
    	webLogService.writeWebLog(log);
        // mongoLog.info(JSONUtils.toJson(log));
    }
}
