package org.example.service;

import org.example.model.dto.LinkDto;
import org.example.model.dto.OrderDto;
import org.example.model.entity.Order;
import org.example.model.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Создаёт новый {@link Order заказ}
     *
     * @return id заказа
     */
    public long generateOrder() {

        long uuid = UUID.randomUUID().getLeastSignificantBits();

        while (orderRepository.existsById(uuid))
            uuid = UUID.randomUUID().getLeastSignificantBits();

        Order order = new Order();
        order.setId(uuid);
        orderRepository.save(order);

        return uuid;
    }

    public boolean isExist(long orderId) {
        return orderRepository.existsById(orderId);
    }

    public OrderDto getOrderDto(long orderId) {

        if (!orderRepository.existsById(orderId))
            throw new RuntimeException("заказ не существует");

        Order order = orderRepository.getReferenceById(orderId);
        List<LinkDto> linkDtoList = order.getLinks().stream().map(link ->
                new LinkDto(link.getLink(), link.getPfCount(), link.getStartDate(), link.getEndDate())
        ).toList();

        return new OrderDto(orderId, linkDtoList);
    }
}
