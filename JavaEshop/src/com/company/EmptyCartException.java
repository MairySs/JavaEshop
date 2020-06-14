package com.company;

public class EmptyCartException extends Exception {
    public EmptyCartException() {
        super("Your cart is empty");
    }
}
