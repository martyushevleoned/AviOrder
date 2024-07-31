package org.example.service;

import org.example.model.dto.LinkDto;
import org.example.model.dto.OrderDto;
import org.example.model.entity.Link;
import org.example.model.entity.Order;
import org.example.model.repository.LinkRepository;
import org.example.model.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final LinkRepository linkRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, LinkRepository linkRepository) {
        this.orderRepository = orderRepository;
        this.linkRepository = linkRepository;
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

    public List<LinkDto> getLinksByOrderId(long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("заказ не существует"));
        return order.getLinks().stream().map(link ->
                new LinkDto(link.getLink(), link.getPfCount(), link.getStartDate(), link.getEndDate())
        ).toList();
    }

    @Transactional
    public void save(OrderDto orderDto) {

        Order order = orderRepository.findById(orderDto.orderId()).orElseThrow(() -> new RuntimeException("заказ не существует"));
        linkRepository.deleteAll(order.getLinks());

        List<Link> newLinks = orderDto.linkDtoList().stream().map(linkDto -> {
            Link link = new Link();
            link.setOrder(order);
            link.setLink(linkDto.link());
            link.setPfCount(linkDto.pfCount());
            link.setStartDate(linkDto.startDate());
            link.setEndDate(linkDto.endDate());
            return link;
        }).toList();
        linkRepository.saveAll(newLinks);
    }
}
