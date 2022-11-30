package com.aladin.retailserviceddd.domain.entity;

import com.aladin.retailserviceddd.domain.vo.Amount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Stock {

    public Amount amount;

    public ProductSku productSku;

    public Stock() {}

    public Stock(ProductSku productSku, Amount amount) {
        this.productSku = productSku;
        this.amount = amount;
    }

    public Amount amount() {
        return amount;
    }

    public Stock add(Amount addend) {
        this.amount.add(addend);
        return this;
    }

    public Stock remove(Amount addend) {
        this.amount.subtract(addend);
        return this;
    }

    public boolean hasEnough(Amount amount) {
        return this.amount.compareTo(amount) != -1;
    }

    public Amount needsYet(Amount amount) {
        return this.amount.compareTo(amount) == 1
                ? Amount.ZERO
                : amount.subtract(this.amount);
    }

    public boolean isSoldOut() {
        return amount.equals(Amount.ZERO);
    }
}
