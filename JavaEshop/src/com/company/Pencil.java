package com.company;

public class Pencil extends Item {
    private double tipSize;
    private String type; // H, B, HB


    public Pencil(String name, String description, double price, int stock, int id, double tipSize, String type) {
        super(name, description, price, stock, id);
        this.tipSize = tipSize;
        this.type = type;
    }

    public String getDetails(){
        return (" Tip Size: " + tipSize +"  ||  "+  " Type: " + type);
    }
}
