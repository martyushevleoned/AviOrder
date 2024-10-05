package org.example.model.dto;

import org.example.model.entity.Advertisement;

import java.time.LocalDate;

public record AdvertisementDto(String link,
                               int productFactorCountPerDay,
                               LocalDate promotionStartDate,
                               LocalDate promotionEndDate,
                               boolean promoteContacts) {

    public static AdvertisementDto createByAdvertisement(Advertisement advertisement) {
        return new AdvertisementDto(
                advertisement.getLink(),
                advertisement.getProductFactorCountPerDay(),
                advertisement.getPromotionStartDate(),
                advertisement.getPromotionEndDate(),
                advertisement.isPromoteContacts()
        );
    }
}
