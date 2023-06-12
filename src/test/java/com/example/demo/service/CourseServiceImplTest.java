package com.example.demo.service;

import com.example.demo.dao.CourseDao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

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

    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        DataSource dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScripts("classpath:db.migration/V1__Create_Random_Group_Name_Function.sql",
                        "classpath:db.migration/V2__Create_Courses_Table.sql",
                        "classpath:db.migration/V3__Create_Students_Table.sql")
                .build();

        jdbcTemplate = new JdbcTemplate(dataSource);
        courseService = new CourseServiceImpl(courseDao);
    }

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
