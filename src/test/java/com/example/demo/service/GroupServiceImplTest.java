package com.example.demo.service;

import com.example.demo.dao.GroupDao;
import com.example.demo.model.Group;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("mock")
class GroupServiceImplTest {

    @InjectMocks
    private GroupServiceImpl groupService;

    @Mock
    private GroupDao groupDao;

    @Test
    void testFindGroupsWithMaxStudents() {
        //given
        int maxStudents = 10;

        List<Group> expectedGroups = new ArrayList<>();


        expectedGroups.add(new Group(1L, "XX-01"));
        expectedGroups.add(new Group(2L, "XX-02"));
        //when
        when(groupDao.findGroupsWithMaxStudents(maxStudents)).thenReturn(expectedGroups);
        //then
        List<Group> actualGroups = groupService.findGroupsWithMaxStudents(maxStudents);

        assertEquals(expectedGroups, actualGroups);
    }

    @Test
    void testFindGroupsWithMaxStudentsThrowingException() {
        //given
        int maxStudents = 10;

        List<Group> expectedGroups = new ArrayList<>();

        //when
        expectedGroups.add(new Group(1L, "XX-01"));
        expectedGroups.add(new Group(2L, "XX-02"));

        //then
        doThrow(new IllegalArgumentException("")).when(groupDao).findGroupsWithMaxStudents(anyInt());

        assertThrows(RuntimeException.class, () ->groupService.findGroupsWithMaxStudents(maxStudents));
    }
}
