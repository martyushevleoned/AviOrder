package org.example.controller;

import org.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class FileController {

    private final OrderService orderService;

    @Autowired
    public FileController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/order/download/{id}")
    @ResponseBody
    public ResponseEntity<Resource> downloadExcel(@PathVariable long id) {

        Resource resource = orderService.generateOrderExcelDocument(id);
        String resourceName = String.format("order-%s.xlsx", id);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resourceName + "\" ")
                .body(resource);
    }
}
