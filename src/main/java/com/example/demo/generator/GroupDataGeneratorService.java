package com.example.demo.generator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class GroupDataGeneratorService {
    public static final int NUM_GROUPS = 10;
    private static final Logger LOGGER  = LogManager.getLogger(GroupDataGeneratorService.class);

    private final JdbcTemplate jdbcTemplate;

    public GroupDataGeneratorService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void generateAndInsertGroups() {
        try {
            generateRandom();
        } catch (SQLException e) {
            LOGGER.error("Failed to generate and insert groups: " + e.getMessage());
        }
    }

    public void generateRandom() throws SQLException {
        List<String> groupNames = generateRandomGroupNames(NUM_GROUPS);
        String sql = "INSERT INTO groups (group_name) VALUES (?)";

        for (String groupName : groupNames) {
            jdbcTemplate.update(sql, groupName);
        }

        LOGGER.info("Group data inserted successfully");
    }

    public List<String> generateRandomGroupNames(int numGroups) {
        List<String> groupNames = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < numGroups; i++) {
            String groupName = generateRandomGroupName(random);
            groupNames.add(groupName);
        }
        return groupNames;
    }

    public String generateRandomGroupName(Random random) {
        return String.valueOf(generateRandomCharacter(random)) +
                generateRandomCharacter(random) +
                "-" +
                generateRandomNumber(random) +
                generateRandomNumber(random);
    }

    public int generateRandomNumber(Random random) {
        int numberStart = 0;
        int numberEnd = 9;
        return random.nextInt(numberEnd - numberStart + 1) + numberStart;
    }

    public char generateRandomCharacter(Random random) {
        int asciiStart = 65; // ASCII code for 'A'
        int asciiEnd = 90; // ASCII code for 'Z'
        int randomAscii = random.nextInt(asciiEnd - asciiStart + 1) + asciiStart;
        return (char) randomAscii;
    }
}
