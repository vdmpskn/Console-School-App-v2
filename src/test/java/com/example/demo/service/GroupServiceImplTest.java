package com.example.demo.service;

import com.example.demo.dao.GroupDao;
import com.example.demo.model.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class GroupServiceImplTest {

    @Mock
    private GroupDao groupDao;

    private GroupService groupService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        groupService = new GroupServiceImpl(groupDao);
    }

    @Test
    public void testFindGroupsWithMaxStudents() {
        int maxStudents = 10;

        List<Group> expectedGroups = new ArrayList<>();
        expectedGroups.add(new Group(1L, "XX-01"));
        expectedGroups.add(new Group(2L, "XX-02"));

        when(groupDao.findGroupsWithMaxStudents(maxStudents)).thenReturn(expectedGroups);

        List<Group> actualGroups = groupService.findGroupsWithMaxStudents(maxStudents);

        assertEquals(expectedGroups, actualGroups);
    }
}
