package com.aladin.retailserviceddd.payment.exception;

public class NotFoundPreDepositedException extends Exception {
    public NotFoundPreDepositedException(String message) {
        super(message);
    }
}