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
        try{
            courseDao.addStudentToCourse(studentId, courseId);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void removeStudentFromCourse(long studentId, long courseId) {
       try{
           courseDao.removeStudentFromCourse(studentId, courseId);
       } catch (Exception e){
           e.printStackTrace();
       }
    }
}
