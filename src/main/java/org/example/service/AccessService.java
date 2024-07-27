package org.example.service;

import org.example.model.entity.Order;
import org.example.model.entity.User;
import org.example.model.repository.OrderRepository;
import org.example.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AccessService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public AccessService(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    /**
     * Вызывает исключение если у пользователя недостаточно прав для редактирования страницы
     */
    public void editAccess(UserDetails userDetails, long orderId) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        Order order = orderRepository.findById(orderId).orElseThrow();
        if (user.getId() != order.getUser().getId())
            throw new RuntimeException(
                    String.format("Пользователь %s не имееет доступа к редактированию заказа %s", userDetails.getUsername(), orderId)
            );
    }
}
