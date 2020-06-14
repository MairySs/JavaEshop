package com.company;

public class StockMustBePositiveException extends Exception{
    public StockMustBePositiveException() {
        super("Stock can not have a negative value");
    }

}
