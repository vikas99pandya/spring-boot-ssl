package main.app.controller;


import main.app.model.ModelClass;
import main.app.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("classes")
@RestController
public class ClassController {

    @Autowired
    ClassService classService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ModelClass>> getClasses() {
        List<ModelClass> list= classService.getClasses();
        return new ResponseEntity<List<ModelClass>>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ModelClass> getSpecificClass(@PathVariable long id) {
        ModelClass modelClass= classService.getClassById(id);
        return new ResponseEntity<ModelClass>(modelClass, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addClass(@RequestBody ModelClass modelClass) {
        classService.addClass(modelClass);
        return new ResponseEntity<ModelClass>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity updateClass(@RequestBody ModelClass modelClass) {
        classService.addClass(modelClass);
        return new ResponseEntity<ModelClass>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public ResponseEntity deleteClass(@PathVariable long id) {
        classService.deleteClass(id);
        return new ResponseEntity<ModelClass>(HttpStatus.OK);
    }

}
