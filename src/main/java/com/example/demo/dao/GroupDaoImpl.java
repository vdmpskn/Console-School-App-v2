package com.example.demo.dao;

import com.example.demo.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GroupDaoImpl implements GroupDao {

    private final JdbcTemplate jdbcTemplate;

    private static final String FIND_GROUP_WITH_MAX_STUDENT = """ 
                SELECT g.id, g.group_name, COUNT(s.id) AS student_count  
                FROM groups g  
                LEFT JOIN students s ON g.id = s.group_id  
                GROUP BY g.id, g.group_name  
                HAVING COUNT(s.id) <= ?; """;

    @Autowired
    public GroupDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Group> findGroupsWithMaxStudents(int maxStudents) {

        return jdbcTemplate.query(FIND_GROUP_WITH_MAX_STUDENT, new Object[]{maxStudents}, (rs, rowNum) -> {
            long groupId = rs.getLong("id");
            String groupName = rs.getString("group_name");
            // You can retrieve other columns from the result set as needed

            // Initialize the Group object with the retrieved values
            Group group = new Group(groupId, groupName);
            // Set other properties of the Group object as needed

            return group;
        });
    }
}
