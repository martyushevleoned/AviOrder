package org.example.model.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "orders")
public class Order {

    @Id
    private Long id;

    @Column(nullable = false)
    private Instant recentView = Instant.now();

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private List<Link> links = new ArrayList<>();

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

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }
}
