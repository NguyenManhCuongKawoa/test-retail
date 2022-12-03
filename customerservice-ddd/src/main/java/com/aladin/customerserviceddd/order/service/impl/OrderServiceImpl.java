package com.aladin.customerserviceddd.order.service.impl;

import com.aladin.customerserviceddd.order.dto.CheckEnoughMoneyRequest;
import com.aladin.customerserviceddd.order.dto.DeductMoneyRequest;
import com.aladin.customerserviceddd.order.dto.OrderDetailRequest;
import com.aladin.customerserviceddd.order.dto.OrderRequest;
import com.aladin.customerserviceddd.order.model.Order;
import com.aladin.customerserviceddd.order.model.OrderDetail;
import com.aladin.customerserviceddd.order.repository.OrderDetailRepository;
import com.aladin.customerserviceddd.order.repository.OrderRepository;
import com.aladin.customerserviceddd.order.service.OrderService;
import com.aladin.customerserviceddd.order.service.PaymentService;
import com.aladin.customerserviceddd.order.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired private OrderRepository orderRepository;
    @Autowired private OrderDetailRepository orderDetailRepository;

    @Autowired private PaymentService paymentService;

    @Autowired private ProductService productService;

    @Override
    public Order saveOrder(OrderRequest orderRequest, String username, String bearerToken) throws Exception {

        Long totalMoney = orderRequest.calcTotalMoney();

        // Check enough money
        CheckEnoughMoneyRequest checkEnoughMoneyRequest = new CheckEnoughMoneyRequest(orderRequest.getPreDepositedId(), totalMoney);
        if(!paymentService.checkEnoughMoney(checkEnoughMoneyRequest, bearerToken)) {
            throw new RuntimeException("You don't enough money to pay for this order");
        }

        // Check enough product
        if(!productService.checkEnoughProduct(orderRequest.getListProduct(), bearerToken)) {
            throw new RuntimeException("The product you have selected is not in stock");
        }

        // Handle order
        Order order = new Order();
        order.setUsername(username);
        order.setPreDepositedId(orderRequest.getPreDepositedId());
        order.setTotalMoney(totalMoney);

        List<OrderDetail> orderDetails = new ArrayList<>();

        for(OrderDetailRequest dt : orderRequest.getListProduct()) {

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setQuantity(dt.getQuantity());
            orderDetail.setProductId(dt.getProductId());
            orderDetail.setPrice(dt.getPrice());

            orderDetails.add(orderDetail);
        }

        orderRepository.saveAndFlush(order);
        orderDetails.stream().forEach(o -> o.setOrderId(order.getId()));
        orderDetailRepository.saveAll(orderDetails);

        // Deduct product
        productService.deductProducts(orderRequest.getListProduct(), bearerToken);

        // Deduct money
        DeductMoneyRequest deductMoneyRequest = new DeductMoneyRequest();
        paymentService.deductMoney(deductMoneyRequest, bearerToken);

        return order;
    }

    @Override
    public Long calcMoneyByDate(Date date) {
        List<Order> orders = orderRepository.findAllByCreatedDate(date);
        return orders.stream().map(order -> order.getTotalMoney()).reduce(0L, (p, n) -> p + n);
    }


}
