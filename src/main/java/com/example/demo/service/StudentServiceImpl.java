package com.example.demo.service;

import com.example.demo.dao.StudentDao;
import com.example.demo.model.Student;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentDao studentDao;

    private static final Log LOGGER = LogFactory.getLog(StudentServiceImpl.class);

    public StudentServiceImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public Student save(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null!");
        }
        try {
            return studentDao.save(student);
        } catch (Exception e) {
            String msg ="Failed  to create student: %s";
            LOGGER.error(msg, e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Student> findAll() {
        try {
            return studentDao.findAll();
        } catch (Exception e) {
            String msg = "Failed  to find all students.";
            LOGGER.error(msg, e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Student> findStudentsByCourseName(String courseName) {
        try {
            return studentDao.findStudentsByCourseName(courseName);
        } catch (Exception e) {
            String msg = String.format("Failed  to find students by course name: %s", courseName);
            LOGGER.error(msg, e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Student findById(Long student_id) {
        try {
            return studentDao.findById(student_id);
        } catch (Exception e) {
            String msg = String.format("Failed  to find student by ID: %s", student_id);
            LOGGER.error(msg, e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Long student_id) {
        try{
            studentDao.deleteById(student_id);
        } catch (Exception e){
            String msg = String.format("Failed  to delete student by ID: %s", student_id);
            LOGGER.error(msg, e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
