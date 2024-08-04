package org.example.model.dto;

import java.time.LocalDate;

public record AdvertisementDto(String link, int pfCount, LocalDate startDate, LocalDate endDate, boolean enableContact) {
}