package employee.info.controller;

import employee.info.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RequestMapping("emp")
@RestController
@Slf4j
public class EmpInfoController {

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Employee> getSpecificClass(@PathVariable long id) {
        Employee emp1= new Employee();
        emp1.setId(1);
        emp1.setName("vikas pandya");

        log.info("comes");
//        String s=null;
//        s.length();
        try {
            Thread.sleep(3000);
        }
        catch(InterruptedException ex){
            log.error("employee thread sleep error");
        }
        return new ResponseEntity<Employee>(emp1, HttpStatus.OK);
    }
}
