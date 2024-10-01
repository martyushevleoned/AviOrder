package org.example.controller;

import org.example.model.constant.Page;
import org.example.model.constant.Resource;
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

    /**
     * {@link Resource#ORDER}
     * {@link Page#EDIT_ORDER}
     */
    @GetMapping("/order")
    public String order(@AuthenticationPrincipal UserDetails userDetails) {
        long orderId = orderService.generateOrder(userDetails);
        return "redirect:"+ Page.EDIT_ORDER.getParamUrl(orderId);
    }

    /**
     * {@link Page#EDIT_ORDER}
     */
    @GetMapping("/order/edit/{orderId}")
    public String editOrderId(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable long orderId,
            Model model
    ) {
        accessService.editOrderAccess(userDetails, orderId);
        model.addAttribute("orderDto", orderService.getOrderDto(orderId));
        return Page.EDIT_ORDER.getTemplate();
    }

    /**
     * {@link Page#VIEW_ORDER}
     */
    @GetMapping("/order/view/{id}")
    public String viewOrderId(
            @PathVariable long id,
            Model model
    ) {
        model.addAttribute("orderDto", orderService.getOrderDto(id));
        return Page.VIEW_ORDER.getTemplate();
    }

    /**
     * {@link Resource#SAVE_ORDER}
     */
    @PutMapping("/order/save")
    @ResponseBody
    public ResponseEntity<String> saveOrder(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody OrderDto orderDto
    ) {
        accessService.editOrderAccess(userDetails, orderDto.orderId());
        orderService.save(orderDto);
        return ResponseEntity.ok("data saved");
    }

    /**
     * {@link Resource#DELETE_ORDER}
     */
    @PostMapping("/order/delete")
    public String deleteOrder(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam long orderId
    ) {
        accessService.editOrderAccess(userDetails, orderId);
        orderService.deleteOrder(orderId);
        return "redirect:" + Page.ACCOUNT.getUrl();
    }
}
