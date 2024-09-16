package org.example.model.dto;

import org.example.model.entity.Advertisement;
import org.example.model.entity.Order;

import java.time.LocalDate;

public record AdvertisementDto(String link, int pfCount, LocalDate startDate, LocalDate endDate, boolean enableContact) {

    public Advertisement toAdvertisement(Order order) {
        Advertisement advertisement = new Advertisement();
        advertisement.setOrder(order);
        advertisement.setLink(link);
        advertisement.setPfCount(pfCount);
        advertisement.setStartDate(startDate);
        advertisement.setEndDate(endDate);
        advertisement.setEnableContact(enableContact);
        return advertisement;
    }
}