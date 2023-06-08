package com.example.demo.service;

import com.example.demo.dao.StudentDao;
import com.example.demo.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StudentServiceImplTest {

    @Mock
    private StudentDao studentDao;

    private StudentService studentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        studentService = new StudentServiceImpl(studentDao);
    }

    @Test
    public void testSaveStudent() {

        Student student = new Student(1L, 1L, "John", "Doe");

        when(studentDao.save(student)).thenReturn(student);

        Student savedStudent = studentService.save(student);

        assertNotNull(savedStudent);
        assertEquals(student.student_id(), savedStudent.student_id());
        assertEquals(student.group_id(), savedStudent.group_id());
        assertEquals(student.first_name(), savedStudent.first_name());
        assertEquals(student.last_name(), savedStudent.last_name());

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
        assertEquals(expectedStudent.student_id(), actualStudent.student_id());
        assertEquals(expectedStudent.group_id(), actualStudent.group_id());
        assertEquals(expectedStudent.first_name(), actualStudent.first_name());
        assertEquals(expectedStudent.last_name(), actualStudent.last_name());

        verify(studentDao, times(1)).findById(studentId);
    }

    @Test
    public void testDeleteStudentById() {
        Long studentId = 1L;

        studentService.deleteById(studentId);

        verify(studentDao, times(1)).deleteById(studentId);
    }
}
