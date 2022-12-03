package com.aladin.customerserviceddd.order.dto;

import java.util.List;

public class OrderRequest {

     private Long preDepositedId;
     private List<OrderDetailRequest> listProduct;


    public Long getPreDepositedId() {
        return preDepositedId;
    }

    public void setPreDepositedId(Long preDepositedId) {
        this.preDepositedId = preDepositedId;
    }

    public List<OrderDetailRequest> getListProduct() {
        return listProduct;
    }

    public void setListProduct(List<OrderDetailRequest> listProduct) {
        this.listProduct = listProduct;
    }

    public Long calcTotalMoney() {
        if(this.listProduct == null || this.listProduct.size() == 0) return 0L;
        return this.listProduct.stream()
                .map(p -> p.getPrice() * p.getQuantity())
                .reduce(0L, (p, n) -> p + n);
    }
}
