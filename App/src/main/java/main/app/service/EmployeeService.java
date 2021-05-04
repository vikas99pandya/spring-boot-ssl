package main.app.service;

import lombok.extern.slf4j.Slf4j;
import main.app.aspect.ExecutionTime;
import main.app.model.ModelEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class EmployeeService {

    @Autowired
    RestTemplate restTemplate;

    @ExecutionTime
    @Cacheable(value="employee", key="#id")
    public ResponseEntity<ModelEmployee > getEmplyee(long id){
        log.info("calling employee information service");
        return restTemplate.getForEntity("https://localhost:8081/emp/"+id, ModelEmployee.class);
    }
}
