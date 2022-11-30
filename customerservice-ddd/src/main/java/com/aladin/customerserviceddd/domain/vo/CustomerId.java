package com.aladin.customerserviceddd.domain.vo;

import com.aladin.customerserviceddd.domain.shared.RandomUUID;

public class CustomerId extends RandomUUID {

    public CustomerId() {
        super();
    }

    public CustomerId(String id) {
        super(id);
    }
    @Override
    protected String getPrefix() {
        return "CUSTOMER-%s";
    }
}