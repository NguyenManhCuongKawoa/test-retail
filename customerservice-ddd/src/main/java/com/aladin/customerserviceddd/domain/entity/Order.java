package com.aladin.customerserviceddd.domain.entity;

import com.aladin.customerserviceddd.domain.shared.RandomUUID;
import com.aladin.customerserviceddd.domain.vo.CustomerId;
import com.aladin.customerserviceddd.domain.vo.OrderDetail;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Order extends RandomUUID {

    private List<OrderDetail> orderDetails;

    private CustomerId customerId;

    private OrderAddress orderAddress;

    public Order() {
        super();
    }

    public Order(String id) {
        super(id);
    }
    @Override
    protected String getPrefix() {
        return "ORDER-%s";
    }
}