package com.aladin.customerserviceddd.order.dto;

import reactor.util.annotation.NonNull;

public class ChangeAmountProductRequest {

    @NonNull
    private Long productId;
    @NonNull
    private Long amount;

    @NonNull
    public Long getProductId() {
        return productId;
    }

    public void setProductId(@NonNull Long productId) {
        this.productId = productId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
