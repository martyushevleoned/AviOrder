package org.example.model.dto;

import java.util.List;

public record OrderDto(long orderId, List<AdvertisementDto> advertisementDtoList) {
}
