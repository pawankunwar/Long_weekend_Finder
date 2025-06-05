package com.pk.project.long_weekend_finder.controller;

import com.pk.project.long_weekend_finder.model.Holiday;
import com.pk.project.long_weekend_finder.model.LongWeekend;
import com.pk.project.long_weekend_finder.service.GoogleCalendarService;
import com.pk.project.long_weekend_finder.service.HolidayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class HolidayController {
    private static final Logger logger = LoggerFactory.getLogger(HolidayController.class);

    // private GoogleCalendarService googleCalendarService;

    @Autowired
    private HolidayService holidayService;

    // public ResponseEntity<List<LongWeekend>> getLongWeekendsFromGoogleCalendar(
    //         @RequestParam String country,
    //         @RequestParam int year) throws IOException {
    //     List<Holiday> holidays = googleCalendarService.getHolidaysForCountry(country, year);
    //     // Process holidays to find long weekends
    //     return ResponseEntity.ok(new ArrayList<>());
    // }

    @GetMapping("/long-weekends")
    public ResponseEntity<List<LongWeekend>> getLongWeekendsFromGoogleCalendar(
            @RequestParam String country,
            @RequestParam int year) {
        logger.info("Fetching long weekends for country: {} and year: {}", country, year);
        List<Holiday> holidays = new ArrayList<>(); // Initialize holidays
        List<LongWeekend> longWeekends = holidayService.findLongWeekends(holidays);
        return ResponseEntity.ok(longWeekends);
    }

    @PostMapping("/upload-excel")
    public ResponseEntity<List<LongWeekend>> getLongWeekendsFromExcel(
            @RequestParam("file") MultipartFile file,
            @RequestParam String country) {
        logger.info("Processing Excel file for country: {}", country);
        try {
            List<Holiday> holidays = holidayService.processExcelFile(file, country);
            List<LongWeekend> longWeekends = holidayService.findLongWeekends(holidays);
            return ResponseEntity.ok(longWeekends);
        } catch (IOException e) {
            logger.error("Error processing Excel file", e);
            return ResponseEntity.badRequest().build();
        }
    }
} 