package com.aladin.retailserviceddd.scheduler;

import com.aladin.retailserviceddd.RetailserviceDddApplication;
import com.aladin.retailserviceddd.order.service.OrderService;
import com.aladin.retailserviceddd.payment.service.PreDepositedHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Scheduler {

    @Autowired
    private OrderService orderService;
    @Autowired
    private PreDepositedHistoryService preDepositedHistoryService;

    private final Logger log = LoggerFactory.getLogger(RetailserviceDddApplication.class);

    @Scheduled(cron = "0 0 23 * * *") // Run everyday at 11:00PM
    public void compareSoldProductAndRetailAccount() {
        log.info("Scheduler job: Comparing the sold product value and retail account balance");

        Date now = new Date();
        Long retailMoney = preDepositedHistoryService.calcTotalMoneyOfOrderByDay(now);
        Long orderMoney = orderService.calcMoneyOrderByDate(now);



    }
}
