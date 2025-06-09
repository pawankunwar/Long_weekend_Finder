package com.pk.project.long_weekend_finder.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.pk.project.long_weekend_finder.Constants.LWFConstants;
import com.pk.project.long_weekend_finder.Enum.CalanderId;
import com.pk.project.long_weekend_finder.model.Holiday;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import com.google.api.services.calendar.Calendar;
import java.util.List;

@Service
public class GoogleCalendarService {

     @Autowired
     private Calendar calendarService;

     @Value("${google.api.key}")
     private String apiKey;

    public List<Holiday> getHolidaysForCountry(String country, int year) throws IOException {
        String calendarId = getHolidayCalendarId(country);
        if (calendarId == null) {
            throw new IllegalArgumentException("Unsupported country: " + country);
        }

        Calendar service;
        try {
            JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
            service = new Calendar.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    jsonFactory,
                    null
            ).setApplicationName("HolidayFetcher").build();
        } catch (GeneralSecurityException e) {
            throw new IOException("Error initializing Google Calendar client", e);
        }

        // Set time range for the given year

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(LWFConstants.DATE_FORMAT_ISO_8601);

        LocalDateTime startDateTime = LocalDate.of(year, 1, 1).atStartOfDay();
        LocalDateTime endDateTime = LocalDate.of(year, 12, 31).atTime(23, 59, 59);

        String startStr = startDateTime.atOffset(ZoneOffset.UTC).format(formatter);
        String endStr = endDateTime.atOffset(ZoneOffset.UTC).format(formatter);

        DateTime start = new DateTime(startStr);
        DateTime end = new DateTime(endStr);

        // Fetch events
        Events events = service.events().list(calendarId)
                .setTimeMin(start)
                .setTimeMax(end)
                .setSingleEvents(true)
                .setOrderBy(LWFConstants.START_TIME)
                .setKey(apiKey)
                .execute();

        List<Holiday> holidays = new ArrayList<>();
        for (Event event : events.getItems()) {
            if (event.getStart() != null && event.getStart().getDate() != null) {
                Holiday holiday = new Holiday();
                holiday.setName(event.getSummary());
                DateTime dateTime = event.getStart().getDate(); // Google DateTime
                LocalDate localDate = Instant.ofEpochMilli(dateTime.getValue())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                holiday.setDate(localDate);
                holiday.setCountry(country);
                holidays.add(holiday);
            }
        }

        return holidays;
    }


    private String getHolidayCalendarId(String country) {
        switch (country.toLowerCase()) {
            case "in":
                return CalanderId.IN.getValue();
            case "us":
            case "usa":
                return CalanderId.US.getValue();
            case "uk":
                return CalanderId.UK.getValue();
            default:
                return null;
        }
    }

} 