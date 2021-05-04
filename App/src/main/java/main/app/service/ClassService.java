package main.app.service;


import main.app.entity.DevClass;
import main.app.entity.Student;
import main.app.model.ModelClass;
import main.app.model.ModelStudent;
import main.app.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassService {
    @Autowired
    private ClassRepository classRepository;


    public List<ModelClass> getClasses(){
        List<DevClass> result = new ArrayList<DevClass>();
        List<ModelClass> resultData = new ArrayList<ModelClass>();
        classRepository.findAll().forEach(result::add);
        resultData = result.stream().map(devClass -> transformClassData(devClass)).collect(Collectors.toList());
        return resultData;
    }

    @Cacheable(value="classes", key="#id")
    public ModelClass getClassById(Long id){
        return transformClassData(classRepository.findById(id).get());
    }

    public void addClass(ModelClass modelClass){
        DevClass devClass=new DevClass();
        devClass.setId(modelClass.getId());
        devClass.setName(modelClass.getName());
        classRepository.save(devClass);
    }


    public void deleteClass(long id){
        classRepository.deleteById(id);
    }

    private ModelClass transformClassData(DevClass devClass){
        ModelClass modelClass = new ModelClass();
        modelClass.setId(devClass.getId());
        modelClass.setName(devClass.getName());
        modelClass.setListStudents(devClass.getStudents().stream().map(s->transformStudentData(s)).collect(Collectors.toList()));
        return modelClass;
    }

    private ModelStudent transformStudentData(Student student){
        ModelStudent modelStudent = new ModelStudent();
        modelStudent.setId(student.getId());
        modelStudent.setName(student.getName());
        modelStudent.setAge(student.getAge());
        modelStudent.setRegistrationNumber(student.getRegistrationNumber());
        return  modelStudent;
    }
}
