package com.fankun.pureRipe.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by fankun on 2017/10/9.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger =  LoggerFactory.getLogger(this.getClass());

    //由于使用了BindingResult，所以这里不会有异常了。
//    @ExceptionHandler(value = BindException.class)
//    public void bindExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception exception) throws Exception {
//        logger.error(exception.getMessage());
//        MethodArgumentNotValidException c = (MethodArgumentNotValidException) exception;
//        List<ObjectError> errors =c.getBindingResult().getAllErrors();
//        StringBuffer errorMsg=new StringBuffer();
//        errors.stream().forEach(x -> errorMsg.append(x.getDefaultMessage()).append(";"));
//        response.sendError(0,errorMsg.toString());
//    }

    @ExceptionHandler(value = Exception.class)
    public String allExceptionHandler(HttpServletRequest request, Exception exception) throws Exception {
        logger.error(exception.getMessage());
        return "redirect:/500";
    }
}
