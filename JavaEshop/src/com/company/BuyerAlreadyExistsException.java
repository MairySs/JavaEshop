package com.company;

public class BuyerAlreadyExistsException extends Exception {
    public BuyerAlreadyExistsException() {
        super("Buyer already exists");
    }
}
