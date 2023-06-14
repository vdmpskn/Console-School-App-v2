package com.example.demo.service;

public interface CourseService {

    void addStudentToCourse(long studentId, long courseId);

    void removeStudentFromCourse(long studentId, long courseId);

}
