package com.example.demo.dao;

import com.example.demo.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;


import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@ActiveProfiles("test-containers")
class StudentDaoImplTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private StudentDaoImpl studentDao;

    @BeforeEach
    public void setUp() {
        studentDao = new StudentDaoImpl(jdbcTemplate);
    }

    @Test
    void save_shouldReturnSavedStudent() {
        //given
        Student student = new Student(700L, 1L, "John", "Doe");
        //when
        Student savedStudent = studentDao.save(student);
        //then
        assertEquals(student, savedStudent);
    }

    @Test
    void deleteById_shouldDeleteStudentWithGivenId() {
        //given
        Long studentId = 1L;

        JdbcTemplate jdbcTemplateMock = Mockito.mock(JdbcTemplate.class);
        StudentDao studentDao = new StudentDaoImpl(jdbcTemplateMock);
        //when
        studentDao.deleteById(studentId);
        //then
        Mockito.verify(jdbcTemplateMock).update(Mockito.anyString(), Mockito.eq(studentId), Mockito.eq(studentId));
    }

}
