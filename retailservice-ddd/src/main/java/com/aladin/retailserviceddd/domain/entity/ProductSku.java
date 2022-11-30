package com.aladin.retailserviceddd.domain.entity;

import com.aladin.retailserviceddd.domain.shared.RandomUUID;

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
