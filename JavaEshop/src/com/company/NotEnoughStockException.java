package com.company;

public class NotEnoughStockException extends Exception {
    public NotEnoughStockException() {
        super("There isn't enough stock");
    }

    public NotEnoughStockException(String message) {
        super(message);
    }
}
