package com.example.demo.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test-containers")
class CourseDaoImplTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private CourseDaoImpl courseDao;

    @BeforeEach
    public void setUp() {
        courseDao = new CourseDaoImpl(jdbcTemplate);
    }

    @Test
    void addStudentToCourse_shouldInsertRecord() {
        //given
        long studentId = 1;
        long courseId = 2;

        //when
        courseDao.addStudentToCourse(studentId, courseId);

        //then
        int resultCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM student_courses WHERE student_id = ? AND course_id = ?", Integer.class, studentId, courseId);
        assertEquals(1, resultCount);
    }

    @Test
    void removeStudentFromCourse_shouldDeleteRecord() {
        //given
        long studentId = 1;
        long courseId = 2;

        //when
        courseDao.removeStudentFromCourse(studentId, courseId);

        //then
        int resultCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM student_courses WHERE student_id = ? AND course_id = ?", Integer.class, studentId, courseId);
        assertEquals(0, resultCount);
    }

}
