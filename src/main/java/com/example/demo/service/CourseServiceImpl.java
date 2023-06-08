package com.example.demo.service;

import com.example.demo.dao.CourseDao;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService{

    private final CourseDao courseDao;

    public CourseServiceImpl(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    @Override
    public void addStudentToCourse(long studentId, long courseId) {
        courseDao.addStudentToCourse(studentId, courseId);
    }

    @Override
    public void removeStudentFromCourse(long studentId, long courseId) {
        courseDao.removeStudentFromCourse(studentId, courseId);
    }
}
