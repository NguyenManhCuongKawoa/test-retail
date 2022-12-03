package com.aladin.customerserviceddd.order.service.impl;

import com.aladin.customerserviceddd.order.dto.CheckEnoughMoneyRequest;
import com.aladin.customerserviceddd.order.dto.DeductMoneyRequest;
import com.aladin.customerserviceddd.order.dto.OrderDetailRequest;
import com.aladin.customerserviceddd.order.service.PaymentService;
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
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Value("${micro.services.retail}")
    private String retailHost;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Boolean checkEnoughMoney(CheckEnoughMoneyRequest request, String bearerToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.set(HttpHeaders.AUTHORIZATION, bearerToken);
            HttpEntity<CheckEnoughMoneyRequest> entity = new HttpEntity<>(request, headers);

            return Boolean.valueOf(restTemplate.exchange(
                    retailHost + "/api/retail/pre-deposited/check-enough-money", HttpMethod.GET, entity, String.class).getBody());
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean deductMoney(DeductMoneyRequest deductMoneyRequest, String bearerToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.set(HttpHeaders.AUTHORIZATION, bearerToken);
            HttpEntity<DeductMoneyRequest> entity = new HttpEntity<>(deductMoneyRequest, headers);

            restTemplate.exchange(
                    retailHost + "/api/retail/pre-deposited/deduct-money", HttpMethod.PUT, entity, String.class).getBody();
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
