package com.example.demo.service;

import com.example.demo.dao.GroupDao;
import com.example.demo.model.Group;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupDao groupDao;

    public GroupServiceImpl(GroupDao groupDao) { this.groupDao = groupDao; }

    @Override
    public List<Group> findGroupsWithMaxStudents(int maxStudents) {
        try{
            return groupDao.findGroupsWithMaxStudents(maxStudents);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}