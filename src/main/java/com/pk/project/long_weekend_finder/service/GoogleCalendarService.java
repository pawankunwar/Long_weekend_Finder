package com.pk.project.long_weekend_finder.service;

import com.pk.project.long_weekend_finder.model.Holiday;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoogleCalendarService {

    // @Autowired
    // private Calendar calendarService;

    public List<Holiday> getHolidaysForCountry(String country, int year) throws IOException {
        // Skip OAuth callback for now
        return new ArrayList<>();
    }

    private String getCalendarIdForCountry(String country) {
        // Return a default calendar ID or handle as needed
        return "default_calendar_id";
    }
} 