package com.company;

public abstract class Item {
    private String name,description;
    private double price;
    private int stock,id;

    public Item(String name, String description, double price, int stock, int id) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getStock() {
        return stock;
    }

    public double getPrice() {
        return price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getBasicInfo(){
        return ("Product name: " + name +"  ||  "
                + " Price:" + price +"  ||  "
                + " Description:" + description +"  ||  "
                + " Stock:" + stock +"  ||  "
                + " ID:" + id +"  ||  ");

        }
        public abstract String getDetails();

    @Override
    public String toString(){
        return (getBasicInfo() + " " + getDetails());

    }

}