package com.example.demo.service;

import com.example.demo.dao.GroupDao;
import com.example.demo.model.Group;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@SpringBootTest
class GroupServiceImplTest {

    @InjectMocks
    private GroupServiceImpl groupService;

    @Mock
    private GroupDao groupDao;

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
        groupService = new GroupServiceImpl(groupDao);
    }


    @Test
    public void testFindGroupsWithMaxStudents() {
        //given
        int maxStudents = 10;

        List<Group> expectedGroups = new ArrayList<>();

        //then
        expectedGroups.add(new Group(1L, "XX-01"));
        expectedGroups.add(new Group(2L, "XX-02"));

        when(groupDao.findGroupsWithMaxStudents(maxStudents)).thenReturn(expectedGroups);

        List<Group> actualGroups = groupService.findGroupsWithMaxStudents(maxStudents);

        assertEquals(expectedGroups, actualGroups);
    }

    @Test
    public void testFindGroupsWithMaxStudentsThrowingException() {
        //given
        int maxStudents = 10;

        List<Group> expectedGroups = new ArrayList<>();

        //then
        expectedGroups.add(new Group(1L, "XX-01"));
        expectedGroups.add(new Group(2L, "XX-02"));


        doThrow(new IllegalArgumentException("")).when(groupDao).findGroupsWithMaxStudents(anyInt());

        assertThrows(RuntimeException.class, () ->groupService.findGroupsWithMaxStudents(maxStudents));
    }
}
