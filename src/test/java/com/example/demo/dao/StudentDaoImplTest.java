package com.example.demo.dao;

import com.example.demo.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class StudentDaoImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    private StudentDaoImpl studentDao;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        studentDao = new StudentDaoImpl(jdbcTemplate);
    }

    @Test
    public void save_shouldReturnSavedStudent() {

        Student student = new Student(1L, 1L, "John", "Doe");

        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), any(Object[].class)))
                .thenReturn(student);

        Student savedStudent = studentDao.save(student);

        assertEquals(student, savedStudent);
        verify(jdbcTemplate).queryForObject(anyString(), any(RowMapper.class), any(Object[].class));
    }

    @Test
    public void findAll_shouldReturnListOfStudents() {
        List<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(new Student(1L, 1L, "John", "Doe"));
        expectedStudents.add(new Student(2L, 1L, "Jane", "Smith"));

        when(jdbcTemplate.query(anyString(), any(RowMapper.class)))
                .thenReturn(expectedStudents);

        List<Student> actualStudents = studentDao.findAll();

        assertEquals(expectedStudents, actualStudents);
        verify(jdbcTemplate).query(anyString(), any(RowMapper.class));
    }

    @Test
    public void findById_shouldReturnStudentWithGivenId() {
        Long studentId = 1L;
        Student expectedStudent = new Student(studentId, 1L, "John", "Doe");

        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), eq(studentId)))
                .thenReturn(expectedStudent);

        Student actualStudent = studentDao.findById(studentId);

        assertEquals(expectedStudent, actualStudent);
        verify(jdbcTemplate).queryForObject(anyString(), any(RowMapper.class), eq(studentId));
    }

    @Test
    public void deleteById_shouldDeleteStudentWithGivenId() {
        Long studentId = 1L;

        studentDao.deleteById(studentId);

        verify(jdbcTemplate).update(anyString(), eq(studentId), eq(studentId));
    }

    @Test
    public void findStudentsByCourseName_shouldReturnListOfStudents() {
        String courseName = "Math";
        List<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(new Student(1L, 1L, "John", "Doe"));
        expectedStudents.add(new Student(2L, 2L, "Jane", "Smith"));

        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(courseName)))
                .thenReturn(expectedStudents);

        List<Student> actualStudents = studentDao.findStudentsByCourseName(courseName);

        assertEquals(expectedStudents, actualStudents);
        verify(jdbcTemplate).query(anyString(), any(RowMapper.class), eq(courseName));
    }

}
