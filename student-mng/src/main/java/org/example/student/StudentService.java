package org.example.student;

import java.util.ArrayList;
import java.util.List;

public class StudentService {
    public static StudentService singleton;
    StudentRepository studentRepository;
    public static StudentService getInstance() {
        if(singleton == null) {
            singleton = new StudentService();
        }
        return singleton;
    }

    StudentService() {
        studentRepository = new StudentRepository();
    }

    public List<Student> getStudentList(){
        return studentRepository.findAll();
    }

    public void addStudent(Student student) {
        studentRepository.addById(student);
    }

    public void removeStudentById(int id) {
        studentRepository.deleteById(id);
    }

    public Student getStudentById(int id) {
        return studentRepository.findById(id);
    }

    public void updateStudent(Student student) {
        studentRepository.updateById(student);
    }

    public List<Student> sortStudent(String column) {
        return studentRepository.sortBy(column);
    }
}
