package com.aladin.retailserviceddd.rest.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@ToString
public class IncrementProductRequest {
    @NotNull
    public String productSku;
    @NotNull
    public Integer amount;
}

