package com.aladin.retailserviceddd.payment.dto;

import reactor.util.annotation.NonNull;

public class PreDepositedRequest {
    private Long id;
    @NonNull
    private Long surplus;
    @NonNull
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NonNull
    public Long getSurplus() {
        return surplus;
    }

    public void setSurplus(@NonNull Long surplus) {
        this.surplus = surplus;
    }

    @NonNull
    public String getType() {
        return type;
    }

    public void setType(@NonNull String type) {
        this.type = type;
    }
}
