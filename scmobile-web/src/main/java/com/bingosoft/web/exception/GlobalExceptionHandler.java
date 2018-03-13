package com.bingosoft.web.exception;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bingosoft.models.msg.BaseMessage;
import com.bingosoft.web.controller.GoodsController;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	/**
     * 所有异常报错
     * @param request
     * @param exception
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value=Exception.class)  
    public BaseMessage allExceptionHandler(HttpServletRequest request,  
            Exception exception) throws Exception  
    {  
        exception.printStackTrace();
//        System.out.println("我报错了："+exception.getLocalizedMessage());
//        System.out.println("我报错了："+exception.getCause());
//        System.out.println("我报错了："+exception.getSuppressed());
//        System.out.println("我报错了："+exception.getMessage());
//        System.out.println("我报错了："+exception.getStackTrace());
        logger.error(exception.getMessage()+exception.getStackTrace());
        return new BaseMessage(100,false,"");  
    }  
}
