package com.aladin.retailserviceddd.stock.api;

import com.aladin.retailserviceddd.stock.dto.ChangeAmountProductRequest;
import com.aladin.retailserviceddd.stock.dto.ProductRequest;
import com.aladin.retailserviceddd.stock.entity.Product;
import com.aladin.retailserviceddd.stock.service.ProductService;
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
public class ProductAPI {
    private final Logger log = LoggerFactory.getLogger(ProductAPI.class);

    @Autowired
    private ProductService productService;

    @GetMapping("")
    public ResponseEntity<List<Product>> getAllProduct()  {
        log.info("REST request to get all Product of retail");

        return ResponseEntity.ok()
                .body(productService.getAllProduct());
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) throws Exception {
        log.info("REST request to save product : {}", product);
        if (product.getId() != null) {
            throw new Exception("A new product cannot already have an ID");
        }
        Product result = productService.save(product);

        return ResponseEntity
                .created(new URI("/api/retail/product/" + result.getId()))
                .body(result);
    }

    @PutMapping("change-amount-product")
    public ResponseEntity<Product> changeAmountProduct(@Valid @RequestBody ChangeAmountProductRequest request) throws Exception {
        log.info("REST request to change amount of product : {}", request);
        if (request.getProductId() == null) {
            throw new Exception("A productId can not null");
        }
        Product result = productService.incrementProduct(request.getProductId(), request.getAmount());

        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping("check-enough-product")
    public ResponseEntity<Long> checkEnoughProduct(@RequestBody List<ProductRequest> request) {
        log.info("REST request to check enough product : {}", request);

        Long result = productService.checkEnoughProduct(request);

        return ResponseEntity
                .ok()
                .body(result);
    }

}
