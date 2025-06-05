package com.pk.project.long_weekend_finder.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class WebController {
    private static final Logger logger = LoggerFactory.getLogger(WebController.class);

    @GetMapping("/")
    public String home(Model model) {
        logger.info("Accessing home page");
        model.addAttribute("countries", List.of(
            Map.of("code", "us", "name", "United States"),
            Map.of("code", "uk", "name", "United Kingdom"),
            Map.of("code", "in", "name", "India"),
            Map.of("code", "au", "name", "Australia"),
            Map.of("code", "ca", "name", "Canada")
        ));
        return "index";
    }

    @GetMapping("/google-calendar")
    public String googleCalendarView(Model model) {
        logger.info("Accessing Google Calendar view");
        model.addAttribute("countries", List.of(
            Map.of("code", "us", "name", "United States"),
            Map.of("code", "uk", "name", "United Kingdom"),
            Map.of("code", "in", "name", "India"),
            Map.of("code", "au", "name", "Australia"),
            Map.of("code", "ca", "name", "Canada")
        ));
        return "google-calendar";
    }

    @GetMapping("/excel-upload")
    public String excelUploadView(Model model) {
        logger.info("Accessing Excel Upload view");
        model.addAttribute("countries", List.of(
            Map.of("code", "us", "name", "United States"),
            Map.of("code", "uk", "name", "United Kingdom"),
            Map.of("code", "in", "name", "India"),
            Map.of("code", "au", "name", "Australia"),
            Map.of("code", "ca", "name", "Canada")
        ));
        return "excel-upload";
    }
} 