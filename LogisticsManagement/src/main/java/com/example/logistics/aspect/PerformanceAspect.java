package com.example.logistics.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceAspect {

    @Around(
        "execution(* com.example.logistics.service.*.*(..))"
    )
    public Object measureExecutionTime(
            ProceedingJoinPoint joinPoint)
            throws Throwable {

        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long end = System.currentTimeMillis();

        System.out.println(
            "Method: "
            + joinPoint.getSignature().getName()
            + " | Execution time: "
            + (end - start)
            + " ms"
        );

        return result;
    }
}