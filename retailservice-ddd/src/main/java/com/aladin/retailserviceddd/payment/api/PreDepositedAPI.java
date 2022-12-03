package com.aladin.retailserviceddd.payment.api;

import com.aladin.retailserviceddd.payment.dto.CheckEnoughMoneyRequest;
import com.aladin.retailserviceddd.payment.dto.DeductMoneyRequest;
import com.aladin.retailserviceddd.payment.dto.IncrementPreDepositedRequest;
import com.aladin.retailserviceddd.payment.dto.PreDepositedRequest;
import com.aladin.retailserviceddd.payment.entity.PreDeposited;
import com.aladin.retailserviceddd.payment.exception.NotFoundPreDepositedException;
import com.aladin.retailserviceddd.payment.service.PreDepositedService;
import com.aladin.retailserviceddd.stock.dto.ProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/retail/pre-deposited")
public class PreDepositedAPI {
    private final Logger log = LoggerFactory.getLogger(PreDepositedAPI.class);
    @Autowired
    private PreDepositedService preDepositedService;

    @GetMapping("/users")
    public ResponseEntity<List<PreDeposited>> getAllPreDepositedByUsername(Authentication authentication)  {
        log.info("REST request to get all PreDeposited of user");

        String username = authentication.getName();

        return ResponseEntity.ok()
                .body(preDepositedService.getAllByUsername(username));
    }

    @PostMapping
    public ResponseEntity<PreDeposited> createPreDeposited(@Valid @RequestBody PreDepositedRequest preDepositedRequest, Authentication authentication) throws Exception {
        log.info("REST request to save PreDeposited : {}", preDepositedRequest);
        if (preDepositedRequest.getId() != null) {
            throw new Exception("A new predeposited cannot already have an ID");
        }
        String username = authentication.getName();
        PreDeposited result = preDepositedService.save(preDepositedRequest, username);

        return ResponseEntity
                .created(new URI("/api/customer/pre-deposited/" + result.getId()))
                .body(result);
    }

    @PutMapping("increment-surplus")
    public ResponseEntity<PreDeposited> incrementSurplus(@Valid @RequestBody IncrementPreDepositedRequest request) throws Exception {
        log.info("REST request to increment surplus of PreDeposited : {}", request);
        if (request.getPreDepositedId() == null) {
            throw new Exception("A preDepositedId can not null");
        }
        PreDeposited result = preDepositedService.incrementSurplus(request.getPreDepositedId(), request.getAmount());

        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping("check-enough-money")
    public ResponseEntity<?> checkEnoughMoney(@RequestBody CheckEnoughMoneyRequest request) throws NotFoundPreDepositedException {
        log.info("REST request to check enough money : {}", request);

        Boolean result = preDepositedService.checkEnoughMoney(request);

        return ResponseEntity
                .ok()
                .body(result);
    }

    @PutMapping("deduct-money")
    public ResponseEntity<?> deductMoney(@Valid @RequestBody DeductMoneyRequest request) throws Exception {
        log.info("REST request to deduct money: {}", request);

        preDepositedService.deductMoney(request);

        return ResponseEntity
                .ok()
                .body("Deduct money of customer is successfully");
    }
}
