package org.example.model.entity;

import jakarta.persistence.*;
import org.example.model.dto.OrderDto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity(name = "orders")
public class Order {

    @Id
    private Long id;

    @Column(nullable = false)
    private Instant recentView = Instant.now();

    @Column(nullable = false)
    private Instant recentEdit = Instant.now();

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Advertisement> advertisements = new LinkedList<>();

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private User user;

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

    public Instant getRecentEdit() {
        return recentEdit;
    }

    public void setRecentEdit(Instant recentEdit) {
        this.recentEdit = recentEdit;
    }

    public List<Advertisement> getAdvertisements() {
        return advertisements;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public OrderDto toDto() {
        return new OrderDto(id, advertisements.stream().map(Advertisement::toDto).toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
