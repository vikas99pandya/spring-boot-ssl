package main.app.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import main.app.aspect.ExecutionTime;
import main.app.exception.AuthenticatedException;
import main.app.exception.ServiceRaceException;
import main.app.model.ModelEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
@Slf4j
public class EmployeeService {

    @Autowired
    private RestTemplate restTemplate;

    private final String SERVICE_NAME= "employeeService";

    @ExecutionTime
    @CircuitBreaker(name = SERVICE_NAME, fallbackMethod="testFallBack")
    @Cacheable(value="employee", key="#id")
    @Retryable(value = { IOException.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public ResponseEntity<ModelEmployee > getEmplyee(long id){
        log.info("calling employee information service");
        return restTemplate.getForEntity("https://localhost:8081/emp/"+id, ModelEmployee.class);
    }

    private  ResponseEntity<String> testFallBack(Exception ex){
        throw new ServiceRaceException("EMP_SERVICE:","Problems occurecd in employee service"+ex.getMessage(),ex);
    }


    @Recover
    public void getBackendResponseFallback(RuntimeException e){
        throw new ServiceRaceException("RETRY_EMP_SERVICE:","Problems occurecd in employee service"+e.getMessage(),e);
    }
}
