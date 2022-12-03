package com.aladin.customerserviceddd.order.service;

import com.aladin.customerserviceddd.order.dto.CheckEnoughMoneyRequest;
import com.aladin.customerserviceddd.order.dto.DeductMoneyRequest;

public interface PaymentService {
    Boolean checkEnoughMoney(CheckEnoughMoneyRequest request, String bearerToken);

    Boolean deductMoney(DeductMoneyRequest deductMoneyRequest, String bearerToken);
}
