package com.example.demo.dao;

import com.example.demo.model.Group;

import java.util.List;

public interface GroupDao {

    List<Group> findGroupsWithMaxStudents(int maxStudents);

}
