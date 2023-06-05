package com.example.demo;

import java.util.List;

public interface StudentService {
    Student save(Student student);

    List<Student> findAll();

    Student findById(Long student_id);

    void deleteById(Long student_id);
}
