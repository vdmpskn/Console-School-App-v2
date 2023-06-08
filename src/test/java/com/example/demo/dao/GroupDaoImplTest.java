package com.example.demo.dao;

import com.example.demo.model.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.PreparedStatementSetter;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GroupDaoImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    private GroupDaoImpl groupDao;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        groupDao = new GroupDaoImpl(jdbcTemplate);
    }

    @Test
    public void findGroupsWithMaxStudents_shouldReturnListOfGroups() {
        int maxStudents = 5;

        List<Group> expectedGroups = new ArrayList<>();
        expectedGroups.add(new Group(1L, "XX-01"));
        expectedGroups.add(new Group(2L, "XX-02"));

        when(jdbcTemplate.query(anyString(), any(PreparedStatementSetter.class), any(RowMapper.class)))
                .thenReturn(expectedGroups);

        List<Group> actualGroups = groupDao.findGroupsWithMaxStudents(maxStudents);

        assertEquals(expectedGroups, actualGroups);
        verify(jdbcTemplate).query(anyString(), any(PreparedStatementSetter.class), any(RowMapper.class));
    }
}

