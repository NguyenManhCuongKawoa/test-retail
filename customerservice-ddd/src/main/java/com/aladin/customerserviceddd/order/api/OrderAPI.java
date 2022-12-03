package com.aladin.customerserviceddd.order.api;

import com.aladin.customerserviceddd.core.config.jwt.JWTFilter;
import com.aladin.customerserviceddd.order.model.Order;
import com.aladin.customerserviceddd.order.dto.OrderRequest;
import com.aladin.customerserviceddd.order.service.OrderService;
import com.aladin.customerserviceddd.order.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("api/customer/order")
public class OrderAPI {

    private final Logger log = LoggerFactory.getLogger(OrderAPI.class);
    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<?> requestOrder(@RequestBody OrderRequest orderRequest, Authentication authentication, HttpServletRequest request) throws Exception {
        String username = authentication.getName();
        String bearerToken = request.getHeader(JWTFilter.AUTHORIZATION_HEADER);
        log.info("Request to order of customer with username {}: {}", username, orderRequest);

        if(orderRequest.getPreDepositedId() == null) {
            throw new Exception("A order product must have preDepositedId");
        }

        if(orderRequest.getListProduct() == null || orderRequest.getListProduct().size() == 0) {
            throw new Exception("A order product must have listProduct");
        }



        Order result = orderService.saveOrder(orderRequest,username, bearerToken);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("money-per-day")
    public ResponseEntity<?> getMoneyPerday() {
        log.info("Request to get total money order of the day");

        Long totalMoney = orderService.calcMoneyByDate(new Date());

        return ResponseEntity.ok(totalMoney);
    }

    @GetMapping
    public ResponseEntity<?> test(HttpServletRequest request) {
        String bearerToken = request.getHeader(JWTFilter.AUTHORIZATION_HEADER);
        Boolean res = productService.deductProduct(102L, 1L, bearerToken);
        return ResponseEntity.ok(res);
    }
}
