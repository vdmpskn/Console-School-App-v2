package com.example.demo.service;

import com.example.demo.dao.CourseDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class CourseServiceImplTest {

    @Mock
    private CourseDao courseDao;

    private CourseService courseService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        courseService = new CourseServiceImpl(courseDao);
    }

    @Test
    public void testAddStudentToCourse() {
        long studentId = 1L;
        long courseId = 2L;

        courseService.addStudentToCourse(studentId, courseId);

        verify(courseDao).addStudentToCourse(studentId, courseId);
    }

    @Test
    public void testRemoveStudentFromCourse() {
        long studentId = 1L;
        long courseId = 2L;

        courseService.removeStudentFromCourse(studentId, courseId);

        verify(courseDao).removeStudentFromCourse(studentId, courseId);
    }
}
