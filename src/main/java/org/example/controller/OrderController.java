package org.example.controller;

import org.example.model.constant.Page;
import org.example.service.AccessService;
import org.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * {@link Page#ORDER}
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final AccessService accessService;

    @Autowired
    public OrderController(OrderService orderService, AccessService accessService) {
        this.orderService = orderService;
        this.accessService = accessService;
    }

    @GetMapping("/{orderId}")
    public String order(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable long orderId
    ) {
        return "order";
    }

    @GetMapping("/create")
    public String createOrder(@AuthenticationPrincipal UserDetails userDetails) {
        long orderId = orderService.createOrder(userDetails);
        return "redirect:" + Page.ORDER.getParamUrl(orderId);
    }

    @PostMapping("/delete")
    public String deleteOrder(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam long orderId
    ) {
        accessService.access(userDetails, orderId);
        orderService.deleteOrder(orderId);
        return "redirect:" + Page.PROFILE.getUrl();
    }
}
