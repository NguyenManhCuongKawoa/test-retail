package com.aladin.retailserviceddd.rest.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@ToString
public class StockRequest {
    @NotNull
    public Integer amount;
}
