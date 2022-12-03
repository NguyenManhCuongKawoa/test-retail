package com.aladin.retailserviceddd.payment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckEnoughMoneyRequest {
    private Long id;
    private Long amount;
}
