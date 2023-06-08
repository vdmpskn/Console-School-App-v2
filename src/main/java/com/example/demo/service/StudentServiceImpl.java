package com.example.demo.service;

import com.example.demo.dao.StudentDao;
import com.example.demo.model.Student;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentDao studentDao;

    public StudentServiceImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public Student save(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null!");
        }
        return studentDao.save(student);
    }

    @Override
    public List<Student> findAll() {
        return studentDao.findAll();
    }

    @Override
    public List<Student> findStudentsByCourseName(String courseName) {
        return studentDao.findStudentsByCourseName(courseName);
    }

    @Override
    public Student findById(Long student_id) {
        return studentDao.findById(student_id);
    }

    @Override
    public void deleteById(Long student_id) {
        studentDao.deleteById(student_id);
    }
}
