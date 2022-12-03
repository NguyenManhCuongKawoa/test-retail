package com.aladin.customerserviceddd.order.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeductMoneyRequest {
    @NonNull
    private Long cusPreDepositedId;

    @NotNull
    private Long retailPreDepositedId;

    @NotNull
    private Long amount;
}
