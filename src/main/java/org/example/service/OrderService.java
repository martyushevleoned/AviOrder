package org.example.service;

import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.Workbook;
import org.example.model.dto.OrderDto;
import org.example.model.dto.ProfileOrderDto;
import org.example.model.entity.Order;
import org.example.model.entity.User;
import org.example.model.exception.OrderNotFoundException;
import org.example.model.repository.AdvertisementRepository;
import org.example.model.repository.OrderRepository;
import org.example.model.repository.UserRepository;
import org.example.utils.WorkbookUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final AdvertisementRepository advertisementRepository;
    private final WorkbookUtils workbookUtils;

    @Autowired
    public OrderService(UserRepository userRepository, OrderRepository orderRepository, AdvertisementRepository advertisementRepository, WorkbookUtils workbookUtils) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.advertisementRepository = advertisementRepository;
        this.workbookUtils = workbookUtils;
    }

    private User findUser(UserDetails userDetails) {
        return userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    /**
     * @return {@link Order#getId() orderId}
     */
    public UUID createOrder(UserDetails userDetails) {

        Order order = new Order();
        order.setName("Новый заказ");
        order.setUser(findUser(userDetails));
        order = orderRepository.save(order);

        return order.getId();
    }

    public List<ProfileOrderDto> getOrdersList(UserDetails userDetails) {
        List<Order> orders = orderRepository.findAllOrderByUser(findUser(userDetails));
        return orders.stream()
                .sorted(Comparator.comparing(Order::getRecentEditTime))
                .map(ProfileOrderDto::createByOrder)
                .toList();
    }

    public void deleteOrder(UUID orderId) {
        orderRepository.deleteById(orderId);
    }

    public OrderDto getOrderDto(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Заказ не найден"));
        return OrderDto.createByOrder(order);
    }

    @Transactional
    public void saveOrder(OrderDto orderDto) {
        Order order = orderRepository.findById(orderDto.id()).orElseThrow(() -> new OrderNotFoundException("Заказ не найден"));
        order.setName(orderDto.name());
        order.setRecentEditTime(Instant.now());
        orderRepository.save(order);
        advertisementRepository.deleteAll(order.getAdvertisements());
        advertisementRepository.saveAll(orderDto.getAdvertisements());
    }

    public Resource generateOrderExcelDocument(UUID orderId) {

        OrderDto orderDto = getOrderDto(orderId);

        try (
                Workbook workbook = workbookUtils.generateWorkbook(orderDto);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()
        ) {
            workbook.write(byteArrayOutputStream);
            return new ByteArrayResource(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Ошибка создания excel документа");
        }
    }
}
