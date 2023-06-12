package com.example.demo.service;

import com.example.demo.dao.GroupDao;
import com.example.demo.model.Group;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;


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
