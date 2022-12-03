package com.aladin.retailserviceddd.order.service.impl;

import com.aladin.retailserviceddd.order.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Date;

@Service
public class OrderServiceImpl implements OrderService {
    private final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Value("${micro.services.customer}")
    private String retailHost;

    @Autowired
    private RestTemplate restTemplate;
    @Override
    public Long calcMoneyOrderByDate(Date date) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity entity = new HttpEntity(headers);

            return Long.valueOf(restTemplate.exchange(
                    retailHost + "/api/customer/order/money-per-day", HttpMethod.GET, entity, String.class).getBody());
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
