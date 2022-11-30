package com.aladin.retailserviceddd.infrastructure.repository;

import com.aladin.retailserviceddd.infrastructure.mapping.StockTable;
import com.fasterxml.jackson.annotation.OptBoolean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockTableRepository extends JpaRepository<StockTable, String> {

    Optional<StockTable> findByProductSku(String productSku);
}
