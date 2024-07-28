package org.example.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.Instant;

@Entity(name = "orders")
public class Order {

    @Id
    private Long id;

    @Column(nullable = false)
    private Instant recentView = Instant.now();

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getRecentView() {
        return recentView;
    }

    public void setRecentView(Instant recentView) {
        this.recentView = recentView;
    }
}
