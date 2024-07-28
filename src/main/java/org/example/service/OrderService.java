package org.example.service;

import org.example.model.entity.Order;
import org.example.model.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * @return id заказа
     */
    public long generateOrder() {

        long uuid = UUID.randomUUID().getLeastSignificantBits();

        while (orderRepository.findById(uuid).isPresent())
            uuid = UUID.randomUUID().getLeastSignificantBits();

        Order order = new Order();
        order.setId(uuid);
        orderRepository.save(order);

        return uuid;
    }

    public boolean isExist(long id){
        return orderRepository.existsById(id);
    }
}
