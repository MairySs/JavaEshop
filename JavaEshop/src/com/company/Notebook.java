package com.company;

public class Notebook extends Item{
    private int sections;

    public Notebook(String name, String description, double price, int stock, int id, int sections) {
        super(name, description, price, stock, id);
        this.sections = sections;
    }

    public String getDetails(){
        return (" Sections: " + sections );
    }
}
