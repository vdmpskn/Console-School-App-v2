package com.example.demo.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

public class CourseDaoImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    private CourseDaoImpl courseDao;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        courseDao = new CourseDaoImpl(jdbcTemplate);
    }

    @Test
    public void addStudentToCourse_shouldCallJdbcTemplateUpdate() {
        long studentId = 1;
        long courseId = 2;

        courseDao.addStudentToCourse(studentId, courseId);

        verify(jdbcTemplate).update(anyString(), anyLong(), anyLong());
    }

    @Test
    public void removeStudentFromCourse_shouldCallJdbcTemplateUpdate() {
        long studentId = 1;
        long courseId = 2;

        courseDao.removeStudentFromCourse(studentId, courseId);

        verify(jdbcTemplate).update(anyString(), anyLong(), anyLong());
    }
}
