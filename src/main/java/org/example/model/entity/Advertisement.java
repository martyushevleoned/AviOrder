package org.example.model.entity;

import jakarta.persistence.*;
import org.example.model.dto.AdvertisementDto;

import java.time.LocalDate;
import java.util.Objects;

@Entity(name = "advertisement")
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
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

    public Long getId() {
        return id;
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

    public AdvertisementDto toDto() {
        return new AdvertisementDto(link, pfCount, startDate, endDate, enableContact);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Advertisement that = (Advertisement) o;
        return Objects.equals(order, that.order) && Objects.equals(link, that.link) && Objects.equals(pfCount, that.pfCount) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(enableContact, that.enableContact);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, link, pfCount, startDate, endDate, enableContact);
    }
}
