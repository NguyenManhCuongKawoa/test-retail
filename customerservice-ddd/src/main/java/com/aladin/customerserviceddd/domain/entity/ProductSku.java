package com.aladin.customerserviceddd.domain.entity;

import com.aladin.customerserviceddd.domain.shared.RandomUUID;

public class ProductSku extends RandomUUID {

    public ProductSku() {
        super();
    }

    public ProductSku(String id) {
        super(id);
    }
    @Override
    protected String getPrefix() {
        return "PRODUCT-%s";
    }
}
