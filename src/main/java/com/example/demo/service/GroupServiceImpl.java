package com.example.demo.service;

import com.example.demo.dao.GroupDao;
import com.example.demo.model.Group;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupDao groupDao;
    private static final Log LOGGER = LogFactory.getLog(GroupServiceImpl.class);

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