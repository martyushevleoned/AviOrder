package org.example.service;

import org.example.model.entity.Order;
import org.example.model.repository.OrderRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AccessService {

    private final OrderRepository orderRepository;

    public AccessService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void access(UserDetails userDetails, long orderId) { //TODO дописать свои исключения
        Order order = orderRepository.findById(orderId).orElseThrow();
        if (!Objects.equals(order.getUser().getUsername(), userDetails.getUsername()))
            throw new RuntimeException();
    }
}
