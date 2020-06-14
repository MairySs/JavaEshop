package com.company;

public class ItemNotFoundException extends Exception {
    public ItemNotFoundException() {
        super("Item not Found");
    }
}
