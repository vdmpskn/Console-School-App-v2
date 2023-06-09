package com.example.demo.dao;

import com.example.demo.model.Group;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@Testcontainers
public class GroupDaoImplTest {

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("test")
            .withUsername("postgres")
            .withPassword("1111");

    @Mock
    private JdbcTemplate jdbcTemplate;

    private GroupDaoImpl groupDao;

    private void migrateDatabase() {
        Flyway flyway = Flyway.configure()
                .dataSource(postgresContainer.getJdbcUrl(), postgresContainer.getUsername(), postgresContainer.getPassword())
                .locations("classpath:db.migration")
                .load();
        flyway.migrate();
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        postgresContainer.start();
        migrateDatabase();

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(postgresContainer.getDriverClassName());
        dataSource.setUrl(postgresContainer.getJdbcUrl());
        dataSource.setUsername(postgresContainer.getUsername());
        dataSource.setPassword(postgresContainer.getPassword());

        jdbcTemplate = new JdbcTemplate(dataSource);
        groupDao = new GroupDaoImpl(jdbcTemplate);
    }

    @Test
    public void findGroupsWithMaxStudents_shouldReturnListOfGroups() {
        int maxStudents = 0;

        List<Group> expectedGroups = new ArrayList<>();
        expectedGroups.add(new Group(12L, "XX-02"));
        expectedGroups.add(new Group(11L, "XX-01"));

        jdbcTemplate.update("INSERT INTO groups (id, group_name) VALUES (?, ?)", 11L, "XX-01");
        jdbcTemplate.update("INSERT INTO groups (id, group_name) VALUES (?, ?)", 12L, "XX-02");

        List<Group> actualGroups = groupDao.findGroupsWithMaxStudents(maxStudents);

        assertEquals(expectedGroups, actualGroups);
    }
}
