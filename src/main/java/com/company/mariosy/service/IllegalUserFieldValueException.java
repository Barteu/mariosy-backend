package com.company.mariosy.service;

public class IllegalUserFieldValueException extends Exception {
    public IllegalUserFieldValueException(String errorMessage) {
        super(errorMessage);
    }
}
