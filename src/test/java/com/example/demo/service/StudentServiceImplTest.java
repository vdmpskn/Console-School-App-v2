package com.example.demo.service;

import com.example.demo.dao.StudentDao;
import com.example.demo.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@Testcontainers
public class StudentServiceImplTest {

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("test")
            .withUsername("postgres")
            .withPassword("1111");

    @Mock
    private StudentDao studentDao;

    private StudentService studentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(postgresContainer.getJdbcUrl());
        dataSource.setUsername(postgresContainer.getUsername());
        dataSource.setPassword(postgresContainer.getPassword());

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        studentService = new StudentServiceImpl(studentDao);
    }

    @Test
    public void testSaveStudent() {
        Student student = new Student(1L, 1L, "John", "Doe");

        when(studentDao.save(student)).thenReturn(student);

        Student savedStudent = studentService.save(student);

        assertNotNull(savedStudent);
        assertEquals(student, savedStudent);

        verify(studentDao, times(1)).save(student);
    }

    @Test
    public void testFindAllStudents() {
        List<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(new Student(1L, 1L, "John", "Doe"));
        expectedStudents.add(new Student(2L, 1L, "Jane", "Smith"));

        when(studentDao.findAll()).thenReturn(expectedStudents);

        List<Student> actualStudents = studentService.findAll();

        assertNotNull(actualStudents);
        assertEquals(expectedStudents.size(), actualStudents.size());
        assertEquals(expectedStudents, actualStudents);

        verify(studentDao, times(1)).findAll();
    }

    @Test
    public void testFindStudentsByCourseName() {
        List<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(new Student(1L, 1L, "John", "Doe"));
        expectedStudents.add(new Student(2L, 1L, "Jane", "Smith"));

        String courseName = "Math";

        when(studentDao.findStudentsByCourseName(courseName)).thenReturn(expectedStudents);

        List<Student> actualStudents = studentService.findStudentsByCourseName(courseName);

        assertNotNull(actualStudents);
        assertEquals(expectedStudents.size(), actualStudents.size());
        assertEquals(expectedStudents, actualStudents);

        verify(studentDao, times(1)).findStudentsByCourseName(courseName);
    }

    @Test
    public void testFindStudentById() {
        Student expectedStudent = new Student(1L, 1L, "John", "Doe");
        Long studentId = 1L;

        when(studentDao.findById(studentId)).thenReturn(expectedStudent);

        Student actualStudent = studentService.findById(studentId);

        assertNotNull(actualStudent);
        assertEquals(expectedStudent, actualStudent);

        verify(studentDao, times(1)).findById(studentId);
    }

    @Test
    public void testDeleteStudentById() {
        Long studentId = 1L;

        studentService.deleteById(studentId);

        verify(studentDao, times(1)).deleteById(studentId);
    }
}
