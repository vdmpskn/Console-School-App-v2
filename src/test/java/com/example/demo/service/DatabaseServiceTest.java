package com.example.demo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test-containers")
class DatabaseServiceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DatabaseService databaseService;

    @Test
    void isDatabaseEmpty_shouldReturnFalseWhenNotEmpty() {
        //when
        boolean isEmpty = databaseService.isDatabaseEmpty();
        //then
        assertFalse(isEmpty);
    }

    @Test
    @Sql({"classpath:clear_tables.sql"
            ,"classpath:db.migration/V1__Create_Random_Group_Name_Function.sql",
            "classpath:db.migration/V2__Create_Courses_Table.sql",
            "classpath:db.migration/V3__Create_Students_Table.sql"})
    void isDatabaseEmpty_shouldReturnTrueWhenEmpty() {
        // when
        boolean isEmpty = databaseService.isDatabaseEmpty();

        // then
        assertTrue(isEmpty);
    }
}