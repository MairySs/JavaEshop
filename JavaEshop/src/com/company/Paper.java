package com.company;

public class Paper extends Item {
    private int weight,pages;

    public Paper(String name, String description, double price, int stock, int id, int weight, int pages) {
        super(name, description, price, stock, id);
        this.weight = weight;
        this.pages = pages;
    }

    public String getDetails(){
        return (" Weight: " + weight +"  ||  "+ " Pages: " + pages);
    }
}
