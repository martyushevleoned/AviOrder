package org.example.service;

import org.apache.poi.ss.usermodel.Workbook;
import org.example.model.dto.OrderDto;
import org.example.model.entity.Order;
import org.example.model.entity.User;
import org.example.model.repository.AdvertisementRepository;
import org.example.model.repository.OrderRepository;
import org.example.model.repository.UserRepository;
import org.example.utils.WorkbookUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
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
    public OrderService(OrderRepository orderRepository, AdvertisementRepository advertisementRepository, UserRepository userRepository, WorkbookUtils workbookUtils) {
        this.orderRepository = orderRepository;
        this.advertisementRepository = advertisementRepository;
        this.userRepository = userRepository;
        this.workbookUtils = workbookUtils;
    }

    /**
     * Сохраняет новый {@link Order заказ} в БД
     *
     * @return id заказа
     */
    public long generateOrder(UserDetails userDetails) {

        long uuid;
        do {
            uuid = UUID.randomUUID().getLeastSignificantBits() & Long.MAX_VALUE;
        } while (orderRepository.existsById(uuid));

        Order order = new Order();
        order.setId(uuid);
        order.setUser(userRepository.findByUsername(userDetails.getUsername()).orElseThrow());
        orderRepository.save(order);

        return uuid;
    }

    /**
     * возвращает все заказы пользователя
     */
    public List<OrderDto> getAllOrdersOfUser(UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        return user.getOrders().stream().sorted(Comparator.comparing(Order::getRecentView)).map(Order::toDto).toList();
    }

    /**
     * Сохраняет информацию об объявлениях в бД
     */
    @Transactional
    public void save(OrderDto orderDto) {

        Order order = orderRepository.findById(orderDto.orderId()).orElseThrow();
        order.setRecentEdit(Instant.now());
        orderRepository.save(order);

        advertisementRepository.deleteAllByOrder(order);
        advertisementRepository.saveAll(orderDto.advertisementDtoList().stream().map(
                advertisementDto -> advertisementDto.toAdvertisement(order)
        ).toList());
    }

    /**
     * Собирает дто заказа
     */
    public OrderDto getOrderDto(long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setRecentView(Instant.now());
        orderRepository.save(order);
        return order.toDto();
    }

    /**
     * собирает excel документ
     */
    public Resource generateOrderExcelDocument(long orderId) {

        OrderDto orderDto = getOrderDto(orderId);

        try (
                Workbook workbook = workbookUtils.generateOrderWorkbook(orderDto);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()
        ) {
            workbook.write(byteArrayOutputStream);
            return new ByteArrayResource(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Ошибка создания excel документа");
        }
    }

    public void deleteOrder(long orderId) {
        orderRepository.deleteById(orderId);
    }
}
