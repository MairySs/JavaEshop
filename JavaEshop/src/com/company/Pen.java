package com.company;

public class Pen extends Item {
    private String color;
    private double tipSize;

    public Pen(String name, String description, double price, int stock, int id, String color, double tipSize) {
        super(name, description, price, stock, id);
        this.color = color;
        this.tipSize = tipSize;
    }

    public String getDetails(){
        return (" Color: " + color +"  ||  "+  " Tip Size: " + tipSize);

    }
}
