package com.pk.project.long_weekend_finder.config;

import com.google.api.services.calendar.Calendar;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class TestConfig {
    
    @Bean
    @Primary
    public Calendar calendarService() {
        return Mockito.mock(Calendar.class);
    }
} 