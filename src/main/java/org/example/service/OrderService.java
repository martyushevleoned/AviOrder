package org.example.service;

import org.apache.poi.ss.usermodel.Workbook;
import org.example.model.dto.AdvertisementDto;
import org.example.model.dto.OrderDto;
import org.example.model.entity.Advertisement;
import org.example.model.entity.Order;
import org.example.model.repository.AdvertisementRepository;
import org.example.model.repository.OrderRepository;
import org.example.utils.WorkbookUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final AdvertisementRepository advertisementRepository;
    private final WorkbookUtils workbookUtils;

    @Autowired
    public OrderService(OrderRepository orderRepository, AdvertisementRepository advertisementRepository, WorkbookUtils workbookUtils) {
        this.orderRepository = orderRepository;
        this.advertisementRepository = advertisementRepository;
        this.workbookUtils = workbookUtils;
    }

    /**
     * Создаёт новый {@link Order заказ}
     *
     * @return id заказа
     */
    public long generateOrder() {

        long uuid;
        do {
            uuid = UUID.randomUUID().getLeastSignificantBits() & Long.MAX_VALUE;
        } while (orderRepository.existsById(uuid));

        Order order = new Order();
        order.setId(uuid);
        orderRepository.save(order);

        return uuid;
    }

    public boolean isExist(long orderId) {
        return orderRepository.existsById(orderId);
    }

    public OrderDto getOrderDto(long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("заказ не существует"));

        List<AdvertisementDto> advertisements = order.getAdvertisements().stream().map(advertisement ->
                new AdvertisementDto(advertisement.getLink(), advertisement.getPfCount(), advertisement.getStartDate(), advertisement.getEndDate(), advertisement.getEnableContact())
        ).toList();

        return new OrderDto(orderId, advertisements);
    }

    @Transactional
    public void save(OrderDto orderDto) {

        Order order = orderRepository.findById(orderDto.orderId()).orElseThrow(() -> new RuntimeException("заказ не существует"));
        advertisementRepository.deleteAll(order.getAdvertisements());

        List<Advertisement> newAdvertisements = orderDto.advertisementDtoList().stream().map(advertisementDto -> {
            Advertisement advertisement = new Advertisement();
            advertisement.setOrder(order);
            advertisement.setLink(advertisementDto.link());
            advertisement.setPfCount(advertisementDto.pfCount());
            advertisement.setStartDate(advertisementDto.startDate());
            advertisement.setEndDate(advertisementDto.endDate());
            advertisement.setEnableContact(advertisementDto.enableContact());
            return advertisement;
        }).toList();
        advertisementRepository.saveAll(newAdvertisements);
    }

    public Resource generateOrderExcelDocument(long orderId) {

        OrderDto orderDto = getOrderDto(orderId);

        try (
                Workbook workbook = workbookUtils.generateOrderWorkbook(orderDto);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            workbook.write(byteArrayOutputStream);
            return new ByteArrayResource(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Ошибка создания excel документа");
        }
    }
}
