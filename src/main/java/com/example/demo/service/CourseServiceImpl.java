package com.example.demo.service;

import com.example.demo.dao.CourseDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService{

    private final CourseDao courseDao;
    private static final Logger LOGGER = LogManager.getLogger(CourseServiceImpl.class);

    public CourseServiceImpl(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    @Override
    public void addStudentToCourse(long studentId, long courseId) {
        try{
            courseDao.addStudentToCourse(studentId, courseId);
        } catch (Exception e){
            String msg = String.format("Failed  to add student %s to course %s",studentId, courseId);
            LOGGER.error(msg, e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeStudentFromCourse(long studentId, long courseId) {
       try{
           courseDao.removeStudentFromCourse(studentId, courseId);
       } catch (Exception e){
           String msg = String.format("Failed  to remove student %s from course %s",studentId, courseId);
           LOGGER.error(msg, e);
           e.printStackTrace();
           throw new RuntimeException(e);
       }
    }
}
