package com.example.demo.service;

import com.example.demo.dao.CourseDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.mockito.Mockito.verify;

@Testcontainers
public class CourseServiceImplTest {

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("test")
            .withUsername("postgres")
            .withPassword("1111");

    @Mock
    private CourseDao courseDao;

    private CourseService courseService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(postgresContainer.getJdbcUrl());
        dataSource.setUsername(postgresContainer.getUsername());
        dataSource.setPassword(postgresContainer.getPassword());

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
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
