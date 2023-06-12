package com.example.demo.service;

import com.example.demo.dao.CourseDao;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@SpringBootTest
class CourseServiceImplTest {

    @InjectMocks
    private CourseServiceImpl courseService;

    @Mock
    private CourseDao courseDao;

      @Test
    public void testAddStudentToCourse() {
        //given
        long studentId = 1L;
        long courseId = 2L;
        //then
        courseService.addStudentToCourse(studentId, courseId);

        verify(courseDao).addStudentToCourse(studentId, courseId);
    }

    @Test
    public void testAddStudentToCourseThrowingException() {
        //given
        long studentId = -1L;
        long courseId = 100L;
        //then
        courseService.addStudentToCourse(studentId, courseId);
        doThrow(new IllegalArgumentException("")).when(courseDao).addStudentToCourse(anyLong(), anyLong());

        assertThrows(RuntimeException.class, () -> courseService.addStudentToCourse(studentId, courseId));
    }

    @Test
    public void testRemoveStudentFromCourse() {
        //given
        long studentId = -1L;
        long courseId = 200L;
        //then
        courseService.removeStudentFromCourse(studentId, courseId);

        verify(courseDao).removeStudentFromCourse(studentId, courseId);
    }

    @Test
    public void testRemoveStudentFromCourseThrowingException() {
        //given
        long studentId = -1L;
        long courseId = 100L;
        //then
        courseService.addStudentToCourse(studentId, courseId);
        doThrow(new IllegalArgumentException("")).when(courseDao).removeStudentFromCourse(anyLong(), anyLong());

        assertThrows(RuntimeException.class, () -> courseService.removeStudentFromCourse(studentId, courseId));
    }
}
