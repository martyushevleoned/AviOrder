package org.example.model.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity(name = "advertisements")
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Order order;

    @Column(nullable = false)
    private String link;

    @Column(nullable = false)
    private int productFactorCountPerDay;

    @Column(nullable = false, columnDefinition = "DATE")
    private LocalDate promotionStartDate;

    @Column(nullable = false, columnDefinition = "DATE")
    private LocalDate promotionEndDate;

    @Column(nullable = false)
    private boolean promoteContacts;

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

    public int getProductFactorCountPerDay() {
        return productFactorCountPerDay;
    }

    public void setProductFactorCountPerDay(int productFactorCountPerDay) {
        this.productFactorCountPerDay = productFactorCountPerDay;
    }

    public LocalDate getPromotionStartDate() {
        return promotionStartDate;
    }

    public void setPromotionStartDate(LocalDate promotionStartDate) {
        this.promotionStartDate = promotionStartDate;
    }

    public LocalDate getPromotionEndDate() {
        return promotionEndDate;
    }

    public void setPromotionEndDate(LocalDate promotionEndDate) {
        this.promotionEndDate = promotionEndDate;
    }

    public boolean isPromoteContacts() {
        return promoteContacts;
    }

    public void setPromoteContacts(boolean promoteContacts) {
        this.promoteContacts = promoteContacts;
    }
}
