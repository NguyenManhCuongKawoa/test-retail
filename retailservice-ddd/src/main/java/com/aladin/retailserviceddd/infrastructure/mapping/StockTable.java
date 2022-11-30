package com.aladin.retailserviceddd.infrastructure.mapping;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Stock_Table")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StockTable {

    @Id
    public String productSku;
    public Integer amount;
}
