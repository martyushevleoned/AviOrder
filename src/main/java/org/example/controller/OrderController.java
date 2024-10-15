package org.example.controller;

import org.example.model.constant.Page;
import org.example.model.dto.OrderDto;
import org.example.service.AccessService;
import org.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
            @PathVariable long orderId,
            Model model
    ) {
        accessService.access(userDetails, orderId);
        model.addAttribute("order", orderService.getOrderDto(orderId));
        return "order";
    }

    @PostMapping("/save")
    public String saveOrder(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody OrderDto orderDto
    ) {
        accessService.access(userDetails, orderDto.id());
        orderService.saveOrder(orderDto);
        return "redirect:" + Page.ORDER.getParamUrl(orderDto.id());
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

    @GetMapping("/download/{id}")
    @ResponseBody
    public ResponseEntity<Resource> downloadExcel(@PathVariable long id) {

        Resource resource = orderService.generateOrderExcelDocument(id);
        String resourceName = String.format("order-%s.xlsx", id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resourceName + "\" ")
                .body(resource);
    }
}
