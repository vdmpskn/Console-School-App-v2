package com.example.demo.service;

import com.example.demo.model.Group;

import java.util.List;

public interface GroupService {
    List<Group> findGroupsWithMaxStudents(int maxStudents);
}



