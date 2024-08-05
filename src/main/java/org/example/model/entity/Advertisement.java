package org.example.model.entity;

import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDate;

@Entity(name = "advertisement")
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Order order;

    @Column(nullable = false)
    private String link;

    @Column(nullable = false)
    private Integer pfCount;

    @Column(nullable = false, columnDefinition = "DATE")
    private LocalDate startDate;

    @Column(nullable = false, columnDefinition = "DATE")
    private LocalDate endDate;

    @Column(nullable = false)
    private Boolean enableContact;

    public Advertisement() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getPfCount() {
        return pfCount;
    }

    public void setPfCount(Integer pfCount) {
        this.pfCount = pfCount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Boolean getEnableContact() {
        return enableContact;
    }

    public void setEnableContact(Boolean enableContact) {
        this.enableContact = enableContact;
    }
}
