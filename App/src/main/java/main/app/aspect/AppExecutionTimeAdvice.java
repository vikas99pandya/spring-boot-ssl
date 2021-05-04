package main.app.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import java.time.Duration;
import java.time.Instant;

/**
 * An aspect
 * To log total time taken by a specific calling microservice
 */

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AppExecutionTimeAdvice {

    @Around("@annotation(main.app.aspect.ExecutionTime)")
    public Object trackExecutionTime(ProceedingJoinPoint jp) throws Throwable {
        Instant startTime = Instant.now();
        Object proceed = jp.proceed();
        Instant endTime = Instant.now();
        Duration executionTime = Duration.between(startTime, endTime);
        if (log.isDebugEnabled()) {
            log.debug("Method : {} took {} ms to get executed", jp.getSignature(), executionTime.toMillis());
        }

        return proceed;
    }
}

