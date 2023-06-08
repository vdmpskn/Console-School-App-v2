package com.example.demo.dao;

import com.example.demo.model.Student;

import java.util.List;

public interface StudentDao {

    Student save(Student student);

    List<Student> findAll();

    List<Student> findStudentsByCourseName(String courseName);

    Student findById(Long student_id);

    void deleteById(Long student_id);
}
