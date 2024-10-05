package org.example.model.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Instant recentEditTime = Instant.now();

    @Column(nullable = false)
    private Instant creationTime = Instant.now();

    @ManyToOne(optional = false)
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getRecentEditTime() {
        return recentEditTime;
    }

    public void setRecentEditTime(Instant recentEditTime) {
        this.recentEditTime = recentEditTime;
    }

    public Instant getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Instant creationTime) {
        this.creationTime = creationTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
