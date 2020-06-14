package com.company;

public class ItemExistsException extends Exception{
    public ItemExistsException() {
        super("This item already exists");
    }


}
