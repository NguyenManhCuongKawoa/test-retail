package com.aladin.retailserviceddd.payment.repository;

import com.aladin.retailserviceddd.payment.entity.PreDeposited;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreDepositedRepository extends JpaRepository<PreDeposited, Long> {
    List<PreDeposited> findAllByUsername(String username);
}
