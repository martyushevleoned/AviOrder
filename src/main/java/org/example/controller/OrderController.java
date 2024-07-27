package org.example.controller;

import org.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/order/" + orderService.generateOrderId();
    }

    @GetMapping("/order/{id}")
    public String order(@PathVariable long id, Model model) {
        model.addAttribute("id", id);
        return "order";
    }
}
