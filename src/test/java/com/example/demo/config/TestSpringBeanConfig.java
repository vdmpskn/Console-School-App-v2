package com.example.demo.config;

import com.example.demo.front.ConsoleMenu;
import com.example.demo.service.InputService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles( {"test-containers", "mock" } )
@Configuration
public class TestSpringBeanConfig {

    @Primary
    @Bean
    public InputService inputService() {
        return Mockito.mock(InputService.class);
    }

    @Primary
    @Bean
    public ConsoleMenu consoleMenu() {
        return Mockito.mock(ConsoleMenu.class);
    }
}
