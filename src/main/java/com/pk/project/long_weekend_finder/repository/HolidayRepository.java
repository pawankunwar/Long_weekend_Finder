package com.pk.project.long_weekend_finder.repository;

import com.pk.project.long_weekend_finder.model.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HolidayRepository extends JpaRepository<Holiday, Long> {
    List<Holiday> findByCountry(String country);
} 