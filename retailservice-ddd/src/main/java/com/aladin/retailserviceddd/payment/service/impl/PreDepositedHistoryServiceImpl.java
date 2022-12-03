package com.aladin.retailserviceddd.payment.service.impl;

import com.aladin.retailserviceddd.payment.entity.PreDepositedHistory;
import com.aladin.retailserviceddd.payment.repository.PreDepositedHistoryRepository;
import com.aladin.retailserviceddd.payment.service.PreDepositedHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PreDepositedHistoryServiceImpl implements PreDepositedHistoryService {

    @Autowired
    private PreDepositedHistoryRepository preDepositedHistoryRepository;

    @Override
    public Long calcTotalMoneyOfOrderByDay(Date date) {
        List<PreDepositedHistory> preDepositedHistories = preDepositedHistoryRepository.findAllByCreatedDate(date);
        return preDepositedHistories.stream().map(order -> order.getAmount()).reduce(0L, (p, n) -> p + n);
    }
}
