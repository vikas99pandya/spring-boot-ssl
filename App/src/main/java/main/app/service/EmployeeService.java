package main.app.service;

import main.app.model.ModelEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmployeeService {

    @Autowired
    RestTemplate restTemplate;

    public ResponseEntity<ModelEmployee > getEmplyee(long id){
        return restTemplate.getForEntity("https://localhost:8081/emp/"+id, ModelEmployee.class);
    }
}
