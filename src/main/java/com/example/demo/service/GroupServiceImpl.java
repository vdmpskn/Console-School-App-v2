package com.example.demo.service;

import com.example.demo.dao.GroupDao;
import com.example.demo.model.Group;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupDao groupDao;
    private static final Logger LOGGER = LogManager.getLogger(GroupServiceImpl.class);

    public GroupServiceImpl(GroupDao groupDao) { this.groupDao = groupDao; }

    @Override
    public List<Group> findGroupsWithMaxStudents(int maxStudents) {
        try{
            return groupDao.findGroupsWithMaxStudents(maxStudents);
        } catch (Exception e){
            String msg = String.format("Failed  to find groups with max students: %s", maxStudents);
            LOGGER.error(msg, e);
            e.printStackTrace();
            throw new RuntimeException("");
        }
    }
}