package org.example.controller;

import org.example.model.dto.OrderDto;
import org.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderRestController {

    private final OrderService orderService;

    @Autowired
    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PutMapping("/order/save")
    public void saveOrder(@RequestBody OrderDto orderDto) {
        orderService.save(orderDto);
    }
}
