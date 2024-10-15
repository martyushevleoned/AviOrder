package org.example.model.dto;

import org.example.model.entity.Advertisement;
import org.example.model.entity.Order;

import java.time.LocalDate;
import java.util.List;

/**
 * ДТО для страницы редактирования заказа
 */
public record OrderDto(long id, String name, List<AdvertisementDto> advertisements) {

    public static OrderDto createByOrder(Order order) {
        List<AdvertisementDto> advertisementsDto = order.getAdvertisements().stream().map(advertisement ->
                new AdvertisementDto(
                        advertisement.getLink(),
                        advertisement.getProductFactorCountPerDay(),
                        advertisement.getPromotionStartDate(),
                        advertisement.getPromotionEndDate(),
                        advertisement.isPromoteContacts()
                )
        ).toList();
        return new OrderDto(order.getId(), order.getName(), advertisementsDto);
    }

    public List<Advertisement> getAdvertisements() {
        Order order = new Order();
        order.setId(id);
        return advertisements.stream().map(advertisementDto -> {
            Advertisement advertisement = new Advertisement();
            advertisement.setLink(advertisementDto.link);
            advertisement.setOrder(order);
            advertisement.setPromotionStartDate(advertisementDto.promotionStartDate);
            advertisement.setPromotionEndDate(advertisementDto.promotionEndDate);
            advertisement.setProductFactorCountPerDay(advertisementDto.productFactorCountPerDay);
            advertisement.setPromoteContacts(advertisementDto.promoteContacts);
            return advertisement;
        }).toList();
    }

    public record AdvertisementDto(String link,
                                   int productFactorCountPerDay,
                                   LocalDate promotionStartDate,
                                   LocalDate promotionEndDate,
                                   boolean promoteContacts) {
    }
}
