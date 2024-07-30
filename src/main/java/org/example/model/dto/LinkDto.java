package org.example.model.dto;

import java.time.LocalDate;

public record LinkDto(String link, int pfCount, LocalDate startDate, LocalDate endDate) {
}