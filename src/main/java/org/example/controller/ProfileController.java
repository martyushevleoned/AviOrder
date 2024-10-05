package org.example.controller;

import org.example.model.constant.Page;
import org.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * {@link Page#PROFILE}
 */
@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final OrderService orderService;

    @Autowired
    public ProfileController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String getProfile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("orders", orderService.getOrdersList(userDetails));
        return Page.PROFILE.getTemplate();
    }
}
