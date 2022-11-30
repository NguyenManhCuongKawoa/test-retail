package com.aladin.retailserviceddd.domain.service;

import com.aladin.retailserviceddd.domain.entity.ProductSku;
import com.aladin.retailserviceddd.domain.entity.Stock;
import com.aladin.retailserviceddd.domain.vo.Amount;
import com.aladin.retailserviceddd.rest.dto.StockRequest;

import java.util.List;

public interface StockService {

    Stock save(StockRequest stock);

    Stock getStockByProductSku(ProductSku productSku);

    Stock leftInStock(ProductSku productSku, Amount amount);

    Stock putIntoStock(ProductSku productSku, Amount amount);

    List<Stock> getAllStock();
}
