package main.app.service;


import main.app.entity.DevClass;
import main.app.entity.Student;
import main.app.model.ModelClass;
import main.app.model.ModelStudent;
import main.app.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    private Map<Long, Student> mapStudents= new HashMap<>();

    public List<ModelStudent> getStudents(){
        List<Student> result = new ArrayList<>();
        List<ModelStudent> resultData = new ArrayList<>();
        studentRepository.findAll().forEach(result::add);
        resultData = result.stream().map(devClass -> transformStudentData(devClass)).collect(Collectors.toList());
        return resultData;
    }


    public ModelStudent getStudent(long id){
        Optional<Student> student = studentRepository.findById(id);
        return student.isPresent()?transformStudentData(student.get()):null;
    }

    public void addStudent(ModelStudent modelStudent){
        studentRepository.save(transformStudentModelData(modelStudent));

    }

    public void updateStudent(ModelStudent modelStudent){
        studentRepository.save(transformStudentModelData(modelStudent));

    }

    public void deleteStudent(long id){
        studentRepository.deleteById(id);
    }

    private ModelStudent transformStudentData(Student student){
        ModelStudent modelStudent =new ModelStudent();
        modelStudent.setId(student.getId());
        modelStudent.setName(student.getName());
        modelStudent.setAge(student.getAge());
        modelStudent.setRegistrationNumber(student.getRegistrationNumber());
        ModelClass modelClass =new ModelClass();
        DevClass devClass = student.getDevClass();
        modelClass.setId(devClass.getId());
        modelClass.setName(devClass.getName());
        modelStudent.setModelClass(modelClass);
        return modelStudent;
    }

    private Student transformStudentModelData(ModelStudent modelStudent ){
        Student student =new Student();
        student.setId(modelStudent.getId());
        student.setName(modelStudent.getName());
        student.setAge(modelStudent.getAge());
        student.setRegistrationNumber(modelStudent.getRegistrationNumber());
        return student;
    }


}
