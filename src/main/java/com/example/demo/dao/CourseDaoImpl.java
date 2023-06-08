package com.example.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CourseDaoImpl implements CourseDao{

   private final JdbcTemplate jdbcTemplate;

   private final String addStudentToCourseQuerry = "INSERT INTO student_courses (student_id, course_id) VALUES (?, ?)";

   private final String removeStudentFromCourseQuerry = "DELETE FROM student_courses WHERE student_id = ? AND course_id = ?";

   public CourseDaoImpl(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate;}

    @Override
    public void addStudentToCourse(long studentId, long courseId) {
        jdbcTemplate.update(addStudentToCourseQuerry, studentId, courseId);
    }

    @Override
    public void removeStudentFromCourse(long studentId, long courseId) {
        jdbcTemplate.update(removeStudentFromCourseQuerry, studentId, courseId);
    }


}
