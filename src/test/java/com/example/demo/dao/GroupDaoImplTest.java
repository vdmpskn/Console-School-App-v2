package com.example.demo.dao;

import com.example.demo.model.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test-containers")
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
        //given
        int maxStudents = 0;
        //when
        List<Group> expectedGroups = new ArrayList<>();

        List<Group> actualGroups = groupDao.findGroupsWithMaxStudents(maxStudents);
        //then
        assertEquals(expectedGroups, actualGroups);
    }
}
