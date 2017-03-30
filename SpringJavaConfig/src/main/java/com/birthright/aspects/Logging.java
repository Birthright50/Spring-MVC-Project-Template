package com.birthright.aspects;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by birthright on 20.03.17.
 */
@Aspect
@Component
@Log4j2
public class Logging {
    @Pointcut("within(com.birthright..*)")
    public void logging(){}


    @After("logging()")
    public void log(JoinPoint joinPoint){

        log.info(joinPoint.toShortString() + " called...");
    }
}
