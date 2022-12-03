package com.aladin.retailserviceddd.payment.dto;

import reactor.util.annotation.NonNull;

public class IncrementPreDepositedRequest {

    @NonNull
    private Long preDepositedId;
    @NonNull
    private Long amount;

    public Long getPreDepositedId() {
        return preDepositedId;
    }

    public void setPreDepositedId(Long preDepositedId) {
        this.preDepositedId = preDepositedId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
