package com.edu.unq.tpi.dapp.grupoB.Eventeando.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggerAspect {

    private Logger logger = LogManager.getLogger(LoggerAspect.class);

    @Around("execution(public * com.edu.unq.tpi.dapp.grupoB.Eventeando.service.*.*(..)))")
    public Object log(ProceedingJoinPoint pjp) throws Throwable {
        logger.info(pjp.getSignature());
        Arrays.asList(pjp.getArgs()).forEach(arg -> logger.info(arg));
        Object proceed = pjp.proceed();

        return proceed;
    }
}
