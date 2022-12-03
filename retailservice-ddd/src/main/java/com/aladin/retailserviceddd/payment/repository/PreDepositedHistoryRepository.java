package com.aladin.retailserviceddd.payment.repository;

import com.aladin.retailserviceddd.payment.entity.PreDepositedHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface PreDepositedHistoryRepository extends JpaRepository<PreDepositedHistory, Long> {
    List<PreDepositedHistory> findAllByCreatedDate(Date date);
}
