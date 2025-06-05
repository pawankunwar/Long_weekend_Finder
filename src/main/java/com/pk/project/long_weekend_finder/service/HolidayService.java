package com.pk.project.long_weekend_finder.service;

import com.pk.project.long_weekend_finder.model.Holiday;
import com.pk.project.long_weekend_finder.model.LongWeekend;
import com.pk.project.long_weekend_finder.repository.HolidayRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class HolidayService {

    @Autowired
    private HolidayRepository holidayRepository;

    // private GoogleCalendarService googleCalendarService;

    public List<Holiday> getHolidaysForCountry(String country) throws IOException {
        // Get current year
        int currentYear = LocalDate.now().getYear();
        
        // Fetch holidays from Google Calendar
        List<Holiday> holidays = new ArrayList<>(); // Initialize holidays
        
        // Save holidays to database
        return holidayRepository.saveAll(holidays);
    }

    public List<Map<String, Object>> findLongWeekends(String country) throws IOException {
        // First, fetch and save holidays for the country
        List<Holiday> holidays = getHolidaysForCountry(country);
        List<Map<String, Object>> longWeekends = new ArrayList<>();

        for (Holiday holiday : holidays) {
            LocalDate date = holiday.getDate();
            DayOfWeek dayOfWeek = date.getDayOfWeek();

            if (dayOfWeek == DayOfWeek.FRIDAY) {
                // Check if next day is weekend
                LocalDate nextDay = date.plusDays(1);
                if (nextDay.getDayOfWeek() == DayOfWeek.SATURDAY) {
                    longWeekends.add(Map.of(
                        "startDate", date,
                        "endDate", nextDay.plusDays(1),
                        "type", "Friday Holiday"
                    ));
                }
            } else if (dayOfWeek == DayOfWeek.MONDAY) {
                // Check if previous day is weekend
                LocalDate prevDay = date.minusDays(1);
                if (prevDay.getDayOfWeek() == DayOfWeek.SUNDAY) {
                    longWeekends.add(Map.of(
                        "startDate", prevDay.minusDays(1),
                        "endDate", date,
                        "type", "Monday Holiday"
                    ));
                }
            }
        }

        return longWeekends;
    }

    public List<LongWeekend> findLongWeekends(List<Holiday> holidays) {
        List<LongWeekend> longWeekends = new ArrayList<>();
        
        for (Holiday holiday : holidays) {
            LocalDate holidayDate = holiday.getDate();
            DayOfWeek dayOfWeek = holidayDate.getDayOfWeek();
            
            // Check for Friday holiday (creates 3-day weekend)
            if (dayOfWeek == DayOfWeek.FRIDAY) {
                LongWeekend weekend = new LongWeekend();
                weekend.setStartDate(holidayDate);
                weekend.setEndDate(holidayDate.plusDays(2));
                weekend.setHolidayName(holiday.getName());
                weekend.setNumberOfDays(3);
                weekend.setCountry(holiday.getCountry());
                longWeekends.add(weekend);
            }
            // Check for Monday holiday (creates 3-day weekend)
            else if (dayOfWeek == DayOfWeek.MONDAY) {
                LongWeekend weekend = new LongWeekend();
                weekend.setStartDate(holidayDate.minusDays(2));
                weekend.setEndDate(holidayDate);
                weekend.setHolidayName(holiday.getName());
                weekend.setNumberOfDays(3);
                weekend.setCountry(holiday.getCountry());
                longWeekends.add(weekend);
            }
            // Check for Thursday holiday (creates 4-day weekend)
            else if (dayOfWeek == DayOfWeek.THURSDAY) {
                LongWeekend weekend = new LongWeekend();
                weekend.setStartDate(holidayDate);
                weekend.setEndDate(holidayDate.plusDays(3));
                weekend.setHolidayName(holiday.getName());
                weekend.setNumberOfDays(4);
                weekend.setCountry(holiday.getCountry());
                longWeekends.add(weekend);
            }
            // Check for Tuesday holiday (creates 4-day weekend)
            else if (dayOfWeek == DayOfWeek.TUESDAY) {
                LongWeekend weekend = new LongWeekend();
                weekend.setStartDate(holidayDate.minusDays(3));
                weekend.setEndDate(holidayDate);
                weekend.setHolidayName(holiday.getName());
                weekend.setNumberOfDays(4);
                weekend.setCountry(holiday.getCountry());
                longWeekends.add(weekend);
            }
        }
        
        return longWeekends;
    }

    public List<Holiday> processExcelFile(MultipartFile file, String country) throws IOException {
        List<Holiday> holidays = new ArrayList<>();
        
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header row
                
                Holiday holiday = new Holiday();
                holiday.setName(row.getCell(0).getStringCellValue());
                holiday.setDate(row.getCell(1).getLocalDateTimeCellValue().toLocalDate());
                holiday.setCountry(country);
                holidays.add(holiday);
            }
        }
        
        return holidays;
    }
} 