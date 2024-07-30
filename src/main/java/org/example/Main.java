package org.example;

import org.example.model.entity.Link;
import org.example.model.entity.Order;
import org.example.model.repository.LinkRepository;
import org.example.model.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class Main implements CommandLineRunner {

    private final OrderRepository orderRepository;
    private final LinkRepository linkRepository;

    @Autowired
    public Main(OrderRepository orderRepository, LinkRepository linkRepository) {
        this.orderRepository = orderRepository;
        this.linkRepository = linkRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }

    @Override
    public void run(String... args) {

        Order order = new Order();
        order.setId(1L);
        orderRepository.save(order);

        Link link1 = new Link();
        link1.setLink("first link");
        link1.setOrder(order);
        link1.setPfCount(21);
        link1.setStartDate(LocalDate.now());
        link1.setEndDate(LocalDate.now());
        linkRepository.save(link1);

        Link link2 = new Link();
        link2.setLink("second link");
        link2.setOrder(order);
        link2.setPfCount(41);
        link2.setStartDate(LocalDate.now());
        link2.setEndDate(LocalDate.of(2024, 8, 3));
        linkRepository.save(link2);
    }
}