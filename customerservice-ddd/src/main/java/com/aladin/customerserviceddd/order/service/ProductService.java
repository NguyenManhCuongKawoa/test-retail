package com.aladin.customerserviceddd.order.service;

import com.aladin.customerserviceddd.order.dto.OrderDetailRequest;

import java.util.List;

public interface ProductService {

    Boolean deductProducts(List<OrderDetailRequest> orderDetailRequests, String bearerToken);
    Boolean deductProduct(Long productId, Long amount, String bearerToken);

    Boolean checkEnoughProduct(List<OrderDetailRequest> orderDetailRequests, String bearerToken);
}
