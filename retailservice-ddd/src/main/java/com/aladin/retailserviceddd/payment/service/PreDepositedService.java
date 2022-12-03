package com.aladin.retailserviceddd.payment.service;


import com.aladin.retailserviceddd.payment.dto.CheckEnoughMoneyRequest;
import com.aladin.retailserviceddd.payment.dto.DeductMoneyRequest;
import com.aladin.retailserviceddd.payment.dto.PreDepositedRequest;
import com.aladin.retailserviceddd.payment.entity.PreDeposited;
import com.aladin.retailserviceddd.payment.exception.NotFoundPreDepositedException;

import java.util.List;

public interface PreDepositedService {
    PreDeposited save(PreDepositedRequest preDepositedRequest, String username);

    PreDeposited getById(Long id) throws NotFoundPreDepositedException;

    PreDeposited update(PreDeposited preDeposited);

    PreDeposited incrementSurplus(Long preDepositedId, Long amount) throws NotFoundPreDepositedException;

    List<PreDeposited> getAllByUsername(String username);

    Boolean checkEnoughMoney(CheckEnoughMoneyRequest request) throws NotFoundPreDepositedException;

    void deductMoney(DeductMoneyRequest request) throws NotFoundPreDepositedException;
}
