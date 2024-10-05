package org.example.model.dto;

import org.example.model.entity.Order;

public record ProfileOrderDto(String name, long id) {
    public static ProfileOrderDto createByOrder(Order order) {
        return new ProfileOrderDto(order.getName(), order.getId());
    }
}
