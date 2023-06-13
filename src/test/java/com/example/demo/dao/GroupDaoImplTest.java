package com.example.demo.dao;

import com.example.demo.model.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test-containers")
@TestPropertySource(locations = "classpath:application-test-containers.yml")
@Sql(
        scripts = {"classpath:clear_tables.sql",
                "classpath:db.migration/V1__Create_Random_Group_Name_Function.sql",
                "classpath:db.migration/V2__Create_Courses_Table.sql",
                "classpath:db.migration/V3__Create_Students_Table.sql"
        },
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class GroupDaoImplTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private GroupDaoImpl groupDao;

    @BeforeEach
    void setUp() {
        groupDao = new GroupDaoImpl(jdbcTemplate);
    }

    @Test
    void findGroupsWithMaxStudents_shouldReturnListOfGroups() {
        int maxStudents = 0;

        List<Group> expectedGroups = new ArrayList<>();
        expectedGroups.add(new Group(12L, "XX-02"));
        expectedGroups.add(new Group(11L, "XX-01"));


        List<Group> actualGroups = groupDao.findGroupsWithMaxStudents(maxStudents);

        assertEquals(expectedGroups, actualGroups);
    }
}
