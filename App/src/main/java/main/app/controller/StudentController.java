package main.app.controller;


import main.app.model.ModelStudent;
import main.app.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("students")
@RestController
public class StudentController {

    @Autowired
    StudentService studentService;

    @RequestMapping(value = "hello", method = RequestMethod.GET)
    public String hello() {
        return "Hello World RESTful with Spring Boot";
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ModelStudent>> getStudents() {
        List<ModelStudent> listStudents =  studentService.getStudents();
        return new ResponseEntity<List<ModelStudent>>(listStudents, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ModelStudent> getSpecificStudents(@PathVariable long id) {
        ModelStudent student =  studentService.getStudent(id);
        return new ResponseEntity<ModelStudent>(student, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void addStudent(@RequestHeader(value="x-auth-user", defaultValue="foo") String userAgent, @RequestBody ModelStudent student) {
        studentService.addStudent(student);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void updateStudent(@RequestHeader(value="x-auth-user", defaultValue="foo") String userAgent, @RequestBody ModelStudent student) {
        studentService.updateStudent(student);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public void deleteStudent(@RequestHeader(value="x-auth-user", defaultValue="foo") String userAgent, @PathVariable long id) {
        studentService.deleteStudent(id);
    }


}
