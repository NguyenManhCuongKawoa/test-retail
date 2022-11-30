package com.aladin.retailserviceddd.domain.service.impl;

import com.aladin.retailserviceddd.domain.entity.ProductSku;
import com.aladin.retailserviceddd.domain.entity.Stock;
import com.aladin.retailserviceddd.domain.service.StockService;
import com.aladin.retailserviceddd.domain.vo.Amount;
import com.aladin.retailserviceddd.infrastructure.mapping.StockTable;
import com.aladin.retailserviceddd.infrastructure.repository.StockTableRepository;
import com.aladin.retailserviceddd.rest.dto.StockRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StockServiceImpl implements StockService {

    @Autowired private StockTableRepository stockTableRepository;

    @Override
    public Stock save(StockRequest stockRequest) {
        Stock stock = new Stock(new ProductSku(), new Amount(stockRequest.getAmount()));

        StockTable stockTable = new StockTable();
        stockTable.setProductSku(stock.getProductSku().id);

        stockTable.setAmount(stockRequest.getAmount());

        stockTableRepository.save(stockTable);

        return stock;
    }

    @Override
    public Stock getStockByProductSku(ProductSku productSku) {
        Optional<StockTable> stockTableOptional = stockTableRepository.findByProductSku(productSku.id);

        if(stockTableOptional.isEmpty()) {
            return null;
        }

        return new Stock(productSku, new Amount(stockTableOptional.get().amount));
    }

    @Override
    public Stock leftInStock(ProductSku productSku, Amount amount) {
        Stock stock = getStockByProductSku(productSku);
        if(stock == null) {
            throw new RuntimeException("Not found product with sku: " + productSku.id);
        }

        if(!stock.hasEnough(amount)) {
            throw new RuntimeException("Product with sku: " + productSku.id + " not enough");
        }

        stock.remove(amount);
        return stock;
    }

    @Override
    public Stock putIntoStock(ProductSku productSku, Amount amount) {
        Stock stock = getStockByProductSku(productSku);
        if(stock == null) {
            stock = new Stock(productSku, amount);
        }

        stock.add(amount);
        return stock;
    }

    @Override
    public List<Stock> getAllStock() {
        List<StockTable> stockTables = stockTableRepository.findAll();

        if(stockTables == null || stockTables.size() == 0)
            return new ArrayList<>();
        return stockTables
                .stream().map(st -> {
                    Stock stock = new Stock();
                    stock.setProductSku(new ProductSku(st.getProductSku()));
                    stock.setAmount(new Amount(st.getAmount()));
                    return stock;
                }).collect(Collectors.toList());
    }
}
