package com.aladin.retailserviceddd.stock.service;

import com.aladin.retailserviceddd.stock.dto.ProductRequest;
import com.aladin.retailserviceddd.stock.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProduct();

    Product save(Product product);

    Product incrementProduct(Long productId, Long amount);

    Long checkEnoughProduct(List<ProductRequest> productRequests);
}
