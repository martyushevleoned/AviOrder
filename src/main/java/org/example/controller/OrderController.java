package org.example.controller;

import org.example.model.dto.OrderDto;
import org.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/order")
    public String order() {
        return "redirect:/order/edit/" + orderService.generateOrder();
    }

    @GetMapping("/order/edit/{id}")
    public String editOrderId(@PathVariable long id, Model model) {
        if (!orderService.isExist(id))
            return "orderNotFound";
        model.addAttribute("id", id);
        model.addAttribute("links", orderService.getLinksByOrderId(id));
        return "editOrder";
    }

    @GetMapping("/order/view/{id}")
    public String viewOrderId(@PathVariable long id, Model model) {
        if (!orderService.isExist(id))
            return "orderNotFound";
        model.addAttribute("id", id);
        model.addAttribute("links", orderService.getLinksByOrderId(id));
        return "viewOrder";
    }

    @PutMapping("/order/save")
    @ResponseBody
    public ResponseEntity<?> saveOrder(@RequestBody OrderDto orderDto) {
        orderService.save(orderDto);
        return ResponseEntity.ok("data saved");
    }
}
