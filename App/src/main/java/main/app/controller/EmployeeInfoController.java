package main.app.controller;

import main.app.model.ModelEmployee;
import main.app.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("employee")
@RestController
public class EmployeeInfoController {

    @Autowired
    EmployeeService employeeService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ModelEmployee> getSpecificClass(@PathVariable long id) {

        return employeeService.getEmplyee(id);
    }
}
