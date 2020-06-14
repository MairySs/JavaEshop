package com.company;

public class NoSuchOptionException extends Exception {
    public NoSuchOptionException() {
        super("Please enter a valid selection");
    }

    public NoSuchOptionException(String message) {
        super("Please enter a valid selection");
    }
}
