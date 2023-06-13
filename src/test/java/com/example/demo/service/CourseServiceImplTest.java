package com.example.demo.service;

import com.example.demo.dao.CourseDao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ActiveProfiles("mock")
@SpringBootTest
class CourseServiceImplTest {

    @InjectMocks
    private CourseServiceImpl courseService;

    @Mock
    private CourseDao courseDao;

    @Test
    void testAddStudentToCourse() {
        //given
        long studentId = 1L;
        long courseId = 2L;
        //then
        courseService.addStudentToCourse(studentId, courseId);

        verify(courseDao).addStudentToCourse(studentId, courseId);
    }

    @Test
    void testAddStudentToCourseThrowingException() {
        //given
        long studentId = -1L;
        long courseId = 100L;
        //then
        courseService.addStudentToCourse(studentId, courseId);
        doThrow(new IllegalArgumentException("")).when(courseDao).addStudentToCourse(anyLong(), anyLong());

        assertThrows(RuntimeException.class, () -> courseService.addStudentToCourse(studentId, courseId));
    }

    @Test
    void testRemoveStudentFromCourse() {
        //given
        long studentId = -1L;
        long courseId = 200L;
        //then
        courseService.removeStudentFromCourse(studentId, courseId);

        verify(courseDao).removeStudentFromCourse(studentId, courseId);
    }

    @Test
    void testRemoveStudentFromCourseThrowingException() {
        //given
        long studentId = -1L;
        long courseId = 100L;
        //then
        courseService.addStudentToCourse(studentId, courseId);
        doThrow(new IllegalArgumentException("")).when(courseDao).removeStudentFromCourse(anyLong(), anyLong());

        assertThrows(RuntimeException.class, () -> courseService.removeStudentFromCourse(studentId, courseId));
    }
}
