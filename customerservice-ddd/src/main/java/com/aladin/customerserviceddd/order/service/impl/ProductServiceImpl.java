package com.aladin.customerserviceddd.order.service.impl;

import com.aladin.customerserviceddd.order.api.OrderAPI;
import com.aladin.customerserviceddd.order.dto.ChangeAmountProductRequest;
import com.aladin.customerserviceddd.order.dto.OrderDetailRequest;
import com.aladin.customerserviceddd.order.service.ProductService;
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
public class ProductServiceImpl implements ProductService {

    private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Value("${micro.services.retail}")
    private String retailHost;

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public Boolean deductProducts(List<OrderDetailRequest> orderDetailRequests, String bearerToken) {
        for(OrderDetailRequest request : orderDetailRequests) {
            if(deductProduct(request.getProductId(), request.getQuantity(), bearerToken) == false) return false;
        }

        return true;
    }

    @Override
    public Boolean deductProduct(Long productId, Long amount, String bearerToken) {
        log.info("Request to deduct product with id: " + productId + " with amout: " + amount);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.set(HttpHeaders.AUTHORIZATION, bearerToken);
            ChangeAmountProductRequest request = new ChangeAmountProductRequest();
            request.setProductId(productId);
            request.setAmount(amount);
            HttpEntity<ChangeAmountProductRequest> entity = new HttpEntity<ChangeAmountProductRequest>(request, headers);

            restTemplate.exchange(
                    retailHost + "/api/retail/product/change-amount-product", HttpMethod.PUT, entity, String.class).getBody();
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean checkEnoughProduct(List<OrderDetailRequest> orderDetailRequests, String bearerToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.set(HttpHeaders.AUTHORIZATION, bearerToken);
            HttpEntity<List<OrderDetailRequest>> entity = new HttpEntity<List<OrderDetailRequest>>(orderDetailRequests, headers);

            Long res = Long.valueOf(restTemplate.exchange(
                    retailHost + "/api/retail/product/check-enough-product", HttpMethod.GET, entity, String.class).getBody());
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
