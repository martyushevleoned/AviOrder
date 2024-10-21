package org.example.service;

import org.example.model.entity.Order;
import org.example.model.exception.AccessDeniedException;
import org.example.model.exception.OrderNotFoundException;
import org.example.model.repository.OrderRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class AccessService {

    private final OrderRepository orderRepository;

    public AccessService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void access(UserDetails userDetails, UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(String.format("Заказ (id=%s) не существует", orderId)));
        if (!Objects.equals(order.getUser().getUsername(), userDetails.getUsername()))
            throw new AccessDeniedException(String.format("Пользователь %s не имеет прав на изменения заказа (id=%s)",userDetails.getUsername(),order));
    }
}
