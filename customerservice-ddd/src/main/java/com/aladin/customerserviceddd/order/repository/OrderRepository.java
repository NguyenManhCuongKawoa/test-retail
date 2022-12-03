package com.aladin.customerserviceddd.order.repository;

import com.aladin.customerserviceddd.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByCreatedDate(Date date);
}
