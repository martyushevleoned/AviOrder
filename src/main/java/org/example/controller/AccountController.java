package org.example.controller;

import org.example.model.constant.Page;
import org.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {

    private final OrderService orderService;

    @Autowired
    public AccountController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/account")
    public String account(
            @AuthenticationPrincipal UserDetails userDetails,
            Model model
    ) {
        model.addAttribute("orders", orderService.getAllOrdersOfUser(userDetails));
        return Page.account;
    }
}
