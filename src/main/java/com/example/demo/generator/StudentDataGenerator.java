package com.example.demo.generator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Repository
public class StudentDataGenerator {

    private static final Logger LOGGER = LogManager.getLogger(StudentDataGenerator.class);

    private final JdbcTemplate jdbcTemplate;

    public StudentDataGenerator(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public final String[] FIRST_NAMES = {"John", "Emma", "Michael", "Sophia", "William", "Olivia", "James", "Ava", "Oliver", "Isabella",
            "Benjamin", "Mia", "Lucas", "Charlotte", "Henry", "Amelia", "Alexander", "Harper", "Daniel", "Evelyn"};
    public final String[] LAST_NAMES = {"Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor",
            "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin", "Thompson", "Garcia", "Martinez", "Clark"};

    public void generateStudents() {
        String sql = "INSERT INTO students (group_id, first_name, last_name) VALUES (?, ?, ?)";
        Random random = new Random();

        try {
            List<Integer> groupIds = getGroupIds();

            if (groupIds.isEmpty()) {
                LOGGER.fatal("No group IDs available. Insert group records into the 'groups' table.");
                throw new IllegalStateException();
            }

            List<Object[]> batchArgs = new ArrayList<>();

            for (int i = 0; i < 200; i++) {
                int groupId = getRandomGroupId(groupIds, random);
                String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
                String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];

                batchArgs.add(new Object[]{groupId, firstName, lastName});
            }

            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Object[] args = batchArgs.get(i);
                    ps.setInt(1, (int) args[0]);
                    ps.setString(2, (String) args[1]);
                    ps.setString(3, (String) args[2]);
                }

                @Override
                public int getBatchSize() {
                    return batchArgs.size();
                }
            });

            LOGGER.info("Students generated successfully.");
        } catch (Exception e) {
            LOGGER.error("Failed to generate students: " + e.getMessage());
        }
    }

    public List<Integer> getGroupIds() {
        String sql = "SELECT id FROM groups";

        try {
            return jdbcTemplate.queryForList(sql, Integer.class);
        } catch (Exception e) {
            LOGGER.error("Failed to retrieve group IDs: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public int getRandomGroupId(List<Integer> groupIds, Random random) {
        if (groupIds.isEmpty()) {
            LOGGER.fatal("No group IDs available");
            throw new IllegalStateException();
        }
        int randomIndex = random.nextInt(groupIds.size());
        return groupIds.get(randomIndex);
    }
}
