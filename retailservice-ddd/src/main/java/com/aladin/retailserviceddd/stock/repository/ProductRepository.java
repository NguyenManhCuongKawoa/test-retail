package com.aladin.retailserviceddd.stock.repository;

import com.aladin.retailserviceddd.stock.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
