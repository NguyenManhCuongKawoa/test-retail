package com.aladin.retailserviceddd.payment.service;

import java.util.Date;

public interface PreDepositedHistoryService {

    Long calcTotalMoneyOfOrderByDay(Date date);
}
