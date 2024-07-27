package org.example.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {

    public long generateOrderId() {
        return UUID.randomUUID().getLeastSignificantBits();
    }
}
