package main.app.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MDCLoggingAspect {
    private static final String CLIENT_ID = "abcxxx";

    @Around("@annotation(SetMdcValues) && execution(* *(..))")
    public Object addMdcLoggingVariables(ProceedingJoinPoint joinPoint) throws Throwable {
        synchronized (this) {
            for (Object signatureArg : joinPoint.getArgs()) {
                    MDC.put("clientId", CLIENT_ID);
            }
        }

        try {
            return joinPoint.proceed();
        } finally {
            MDC.remove(CLIENT_ID);
        }
    }

}