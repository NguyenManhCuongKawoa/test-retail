package com.aladin.customerserviceddd.order.service;


import com.aladin.customerserviceddd.order.dto.OrderRequest;
import com.aladin.customerserviceddd.order.model.Order;

import java.util.Date;

public interface OrderService {
    Order saveOrder(OrderRequest orderRequest, String username, String bearerToken) throws Exception;

    Long calcMoneyByDate(Date date);
}
