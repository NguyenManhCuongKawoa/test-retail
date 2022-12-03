package com.aladin.retailserviceddd.payment.service.impl;

import com.aladin.retailserviceddd.core.utils.DBConstants;
import com.aladin.retailserviceddd.payment.dto.CheckEnoughMoneyRequest;
import com.aladin.retailserviceddd.payment.dto.DeductMoneyRequest;
import com.aladin.retailserviceddd.payment.dto.PreDepositedRequest;
import com.aladin.retailserviceddd.payment.entity.PreDeposited;
import com.aladin.retailserviceddd.payment.entity.PreDepositedHistory;
import com.aladin.retailserviceddd.payment.exception.NotFoundPreDepositedException;
import com.aladin.retailserviceddd.payment.repository.PreDepositedHistoryRepository;
import com.aladin.retailserviceddd.payment.repository.PreDepositedRepository;
import com.aladin.retailserviceddd.payment.service.PreDepositedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PreDepositedServiceImpl implements PreDepositedService {

    @Autowired
    private PreDepositedRepository preDepositedRepository;
    @Autowired
    private PreDepositedHistoryRepository preDepositedHistoryRepository;

    @Override
    public PreDeposited save(PreDepositedRequest preDepositedRequest, String username) {
        PreDeposited deposited = new PreDeposited();
        deposited.setSurplus(preDepositedRequest.getSurplus());
        deposited.setType(preDepositedRequest.getType());
        deposited.setUsername(username);

        preDepositedRepository.saveAndFlush(deposited);

        PreDepositedHistory preDepositedHistory = new PreDepositedHistory();
        preDepositedHistory.setPreDepositedId(deposited.getId());
        preDepositedHistory.setAmount(preDepositedRequest.getSurplus());
        preDepositedHistory.setType(DBConstants.PRE_DEPOSITED_HIS_PAYMENT);
        preDepositedHistoryRepository.save(preDepositedHistory);

        return deposited;
    }

    @Override
    public PreDeposited getById(Long id) throws NotFoundPreDepositedException {
        Optional<PreDeposited> preDepositedOptional = preDepositedRepository.findById(id);
        if(preDepositedOptional.isEmpty()) {
            throw new NotFoundPreDepositedException("Not found pre deposited with id: " + id);
        }

        return  preDepositedOptional.get();
    }

    @Override
    public PreDeposited update(PreDeposited preDeposited) {
        return preDepositedRepository.save(preDeposited);
    }

    @Override
    public PreDeposited incrementSurplus(Long preDepositedId, Long amount) throws NotFoundPreDepositedException {

        PreDeposited preDeposited = this.getById(preDepositedId);
        preDeposited.insertMoney(amount);
        preDepositedRepository.save(preDeposited);

        PreDepositedHistory preDepositedHistory = new PreDepositedHistory();
        preDepositedHistory.setPreDepositedId(preDeposited.getId());
        preDepositedHistory.setAmount(amount);
        preDepositedHistory.setType(DBConstants.PRE_DEPOSITED_HIS_PAYMENT);
        preDepositedHistoryRepository.save(preDepositedHistory);

        return preDeposited;
    }

    @Override
    public List<PreDeposited> getAllByUsername(String username) {
        return preDepositedRepository.findAllByUsername(username);
    }

    @Override
    public Boolean checkEnoughMoney(CheckEnoughMoneyRequest request) throws NotFoundPreDepositedException {
        PreDeposited preDeposited = this.getById(request.getId());

        return preDeposited.enoughMoney(request.getAmount());
    }

    @Override
    public void deductMoney(DeductMoneyRequest request) throws NotFoundPreDepositedException {
        PreDeposited cusPreDeposited = this.getById(request.getCusPreDepositedId());
        PreDeposited retailPreDeposited = this.getById(request.getRetailPreDepositedId());

        cusPreDeposited.depositMoney(request.getAmount());
        retailPreDeposited.insertMoney(request.getAmount());

        preDepositedRepository.save(cusPreDeposited);
        preDepositedRepository.save(retailPreDeposited);

        PreDepositedHistory preDepositedHistoryCustomer = new PreDepositedHistory();
        preDepositedHistoryCustomer.setPreDepositedId(cusPreDeposited.getId());
        preDepositedHistoryCustomer.setAmount(0 - request.getAmount());
        preDepositedHistoryCustomer.setType(DBConstants.PRE_DEPOSITED_HIS_ORDER);
        preDepositedHistoryRepository.save(preDepositedHistoryCustomer);

        PreDepositedHistory preDepositedHistoryRetail = new PreDepositedHistory();
        preDepositedHistoryRetail.setPreDepositedId(retailPreDeposited.getId());
        preDepositedHistoryRetail.setAmount(request.getAmount());
        preDepositedHistoryRetail.setType(DBConstants.PRE_DEPOSITED_HIS_ORDER);
        preDepositedHistoryRepository.save(preDepositedHistoryCustomer);
    }
}
