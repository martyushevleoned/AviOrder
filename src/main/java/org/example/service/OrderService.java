package org.example.service;

import org.example.model.dto.ProfileOrderDto;
import org.example.model.entity.Order;
import org.example.model.entity.User;
import org.example.model.repository.OrderRepository;
import org.example.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    private User findUser(UserDetails userDetails) {
        return userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    /**
     * @return {@link Order#getId() orderId}
     */
    public long createOrder(UserDetails userDetails) {

        long uuid;
        do {
            uuid = UUID.randomUUID().getLeastSignificantBits() & Long.MAX_VALUE;
        } while (orderRepository.existsById(uuid));

        Order order = new Order();
        order.setId(uuid);
        order.setName("Заказ: " + uuid);
        order.setUser(findUser(userDetails));
        orderRepository.save(order);

        return uuid;
    }

    public List<ProfileOrderDto> getOrdersList(UserDetails userDetails) {
        List<Order> orders = orderRepository.findAllOrderByUser(findUser(userDetails));
        return orders.stream()
                .sorted(Comparator.comparing(Order::getRecentEditTime))
                .map(ProfileOrderDto::createByOrder)
                .toList();
    }
}
