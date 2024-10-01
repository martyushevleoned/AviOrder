package org.example.model.repository;

import org.example.model.entity.Advertisement;
import org.example.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {

    void deleteAllByOrder(Order order);
}
