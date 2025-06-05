package com.pk.project.long_weekend_finder.model;

import lombok.Data;
import java.time.LocalDate;

@Data
public class LongWeekend {
    private LocalDate startDate;
    private LocalDate endDate;
    private String holidayName;
    private int numberOfDays;
    private String country;
} 