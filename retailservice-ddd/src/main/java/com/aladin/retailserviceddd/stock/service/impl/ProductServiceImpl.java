package com.aladin.retailserviceddd.stock.service.impl;

import com.aladin.retailserviceddd.stock.dto.ProductRequest;
import com.aladin.retailserviceddd.stock.entity.Product;
import com.aladin.retailserviceddd.stock.repository.ProductRepository;
import com.aladin.retailserviceddd.stock.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product incrementProduct(Long productId, Long amount) {
        Optional<Product> productOptional = productRepository.findById(productId);

        if(productOptional.isEmpty()) {
            throw new RuntimeException("Product not found with id: " + productId);
        }

        if(amount < 0) {
            throw new RuntimeException("amount must be greater than zero");
        }

        Product product = productOptional.get();
        product.setQuantity(product.getQuantity() + amount);

        return productRepository.save(product);
    }

    @Override
    public Long checkEnoughProduct(List<ProductRequest> productRequests) {
        for(ProductRequest productRequest : productRequests) {
            Optional<Product> productOptional = productRepository.findById(productRequest.getProductId());

            if(productOptional.isEmpty() || !productOptional.get().enoughQuantity(productRequest.getQuantity())) {
                return productRequest.getProductId();
            }
        }
        return -1L;
    }
}
