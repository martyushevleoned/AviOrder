package org.example.model.dto;

import org.example.model.entity.Order;

import java.util.UUID;

public record ProfileOrderDto(String name, UUID id) {
    public static ProfileOrderDto createByOrder(Order order) {
        return new ProfileOrderDto(order.getName(), order.getId());
    }
}
