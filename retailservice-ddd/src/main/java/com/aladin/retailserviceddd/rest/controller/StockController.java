package com.aladin.retailserviceddd.rest.controller;

import com.aladin.retailserviceddd.domain.entity.ProductSku;
import com.aladin.retailserviceddd.domain.entity.Stock;
import com.aladin.retailserviceddd.domain.service.StockService;
import com.aladin.retailserviceddd.domain.vo.Amount;
import com.aladin.retailserviceddd.rest.dto.IncrementProductRequest;
import com.aladin.retailserviceddd.rest.dto.StockRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/retail/product")
public class StockController {
    private final Logger log = LoggerFactory.getLogger(StockController.class);

    @Autowired
    private StockService stockService;

    @GetMapping("")
    public ResponseEntity<List<Stock>> getAllStock()  {
        log.info("REST request to get all Product of retail");

        return ResponseEntity.ok()
                .body(stockService.getAllStock());
    }

    @PostMapping
    public ResponseEntity<Stock> addNewProduct(@Valid @RequestBody StockRequest product) throws Exception {
        log.info("REST request to save product : {}", product);

        Stock result = stockService.save(product);

        return ResponseEntity
                .created(new URI("/api/retail/product/" + result.productSku))
                .body(result);
    }

    @PutMapping("increment-product")
    public ResponseEntity<Stock> incrementProduct(@Valid @RequestBody IncrementProductRequest request) throws Exception {
        log.info("REST request to increment product : {}", request);
        if (request.getProductSku() == null) {
            throw new Exception("A product sku can not null");
        }
        Stock result = stockService.putIntoStock(new ProductSku(request.getProductSku()), new Amount(request.getAmount()));

        return ResponseEntity
                .ok()
                .body(result);
    }

}

