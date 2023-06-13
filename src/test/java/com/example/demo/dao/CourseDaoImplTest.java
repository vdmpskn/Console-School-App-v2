package com.example.demo.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test-containers")
@Sql(
        scripts = {"classpath:clear_tables.sql",
                "classpath:db.migration/V1__Create_Random_Group_Name_Function.sql",
                "classpath:db.migration/V2__Create_Courses_Table.sql",
                "classpath:db.migration/V3__Create_Students_Table.sql"
        },
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class CourseDaoImplTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private CourseDaoImpl courseDao;

    @BeforeEach
    public void setUp() {
        courseDao = new CourseDaoImpl(jdbcTemplate);
    }

    @Test
    void addStudentToCourse_shouldInsertRecord() {
        //given
        long studentId = 1;
        long courseId = 2;
        int initCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM student_courses WHERE student_id = ? AND course_id = ?", Integer.class, studentId, courseId);

        //when
        courseDao.addStudentToCourse(studentId, courseId);

        //then
        int resultCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM student_courses WHERE student_id = ? AND course_id = ?", Integer.class, studentId, courseId);
        assertEquals(1, initCount);
        assertEquals(2, resultCount);
    }

    @Test
    void removeStudentFromCourse_shouldDeleteRecord() {
        long studentId = 1;
        long courseId = 2;

        courseDao.removeStudentFromCourse(studentId, courseId);

        int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM student_courses WHERE student_id = ? AND course_id = ?", Integer.class, studentId, courseId);
        assertEquals(0, count);
    }

}
