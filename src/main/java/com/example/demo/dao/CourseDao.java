package com.example.demo.dao;

public interface CourseDao {

     void addStudentToCourse(long studentId, long courseId);

     void removeStudentFromCourse(long studentId, long courseId);

}
