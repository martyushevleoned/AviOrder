package org.example.controller;

import org.example.model.constant.Page;
import org.example.model.dto.OrderDto;
import org.example.service.AccessService;
import org.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class OrderController {

    private final OrderService orderService;
    private final AccessService accessService;

    @Autowired
    public OrderController(OrderService orderService, AccessService accessService) {
        this.orderService = orderService;
        this.accessService = accessService;
    }

    @GetMapping("/order")
    public String order(@AuthenticationPrincipal UserDetails userDetails) {
        return "redirect:/order/edit/" + orderService.generateOrder(userDetails);
    }

    @GetMapping("/order/edit/{orderId}")
    public String editOrderId(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable long orderId,
            Model model
    ) {
        accessService.editAccess(userDetails, orderId);
        model.addAttribute("orderDto", orderService.getOrderDto(orderId));
        return Page.editOrder;
    }

    @GetMapping("/order/view/{id}")
    public String viewOrderId(
            @PathVariable long id,
            Model model
    ) {
        model.addAttribute("orderDto", orderService.getOrderDto(id));
        return Page.viewOrder;
    }

    @PutMapping("/order/save")
    @ResponseBody
    public ResponseEntity<String> saveOrder(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody OrderDto orderDto
    ) {
        accessService.editAccess(userDetails, orderDto.orderId());
        orderService.save(orderDto);
        return ResponseEntity.ok("data saved");
    }

    @PostMapping("/order/delete")
    public String deleteOrder(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam long orderId
    ) {
        accessService.editAccess(userDetails, orderId);
        orderService.deleteOrder(orderId);
        return "redirect:/account";
    }
}
