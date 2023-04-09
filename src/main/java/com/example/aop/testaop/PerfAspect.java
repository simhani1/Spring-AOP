package com.example.aop.testaop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Component
@Aspect
public class PerfAspect {

    // 경로지정 방식
    /*@Around("execution(* com.example.aop.service..*.*(..))")
    public Object logPerf(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("[Service 메서드 호출 전 Advice 로직 실행]");
        Object retVal = pjp.proceed();
        System.out.println("[Service 메서드 호출 후 Advice 로직 실행]");
        return retVal;
    }*/

    // 어노테이션 적용 방식
    /*@Around("@annotation(com.example.aop.annotation.PerLogging)")
    public Object logPerf(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("[Service 메서드 호출 전 Advice 로직 실행]");
        Object retVal = pjp.proceed();
        System.out.println("[Service 메서드 호출 후 Advice 로직 실행]");
        return retVal;
    }*/

    // 스프링 빈의 모든 메서드에 적용할 수 있는 기능
    @Around("bean(eventServiceImpl)")
    public Object logPerf(ProceedingJoinPoint pjp) throws Throwable {
        // 메서드 정보 받아오기
        Method method = getMethod(pjp);
        log.info("===== method name ==== {} =====", method.getName());

        // 파라미터 받아오기
        Object[] args = pjp.getArgs();
        if(args.length <= 0)
            log.info("no parameter");
        for (Object arg : args) {
            log.info("parameter type = {}", arg.getClass().getSimpleName());
            log.info("parameter value = {}", arg);
        }

        System.out.println("[Service 메서드 호출 전 Advice 로직 실행]");
        Object retVal = pjp.proceed();
        System.out.println("[Service 메서드 호출 후 Advice 로직 실행]");
        System.out.println();
        return retVal;
    }

    private Method getMethod(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        return signature.getMethod();
    }
}
