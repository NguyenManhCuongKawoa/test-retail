package com.aladin.customerserviceddd.domain.entity;

import com.aladin.customerserviceddd.domain.shared.RandomUUID;
import com.aladin.customerserviceddd.domain.vo.CustomerId;
import com.aladin.customerserviceddd.domain.vo.OrderDetail;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderAddress  extends RandomUUID {

    private String state;
    private String city;
    private String country;

    public OrderAddress() {
        super();
    }

    public OrderAddress(String id) {
        super(id);
    }
    @Override
    protected String getPrefix() {
        return "ORDER-ADDRESS-%s";
    }
}