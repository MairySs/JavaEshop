package com.company;

import java.util.ArrayList;

public class ItemOrdered {
    public Item item;
    private int quantity;


    public ItemOrdered(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public Item getItem() {
        return item;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void QuantityUpdate(int q) throws NotEnoughStockException {
        if(q>item.getStock()) throw new NotEnoughStockException();
        else{
            item.setStock(item.getStock()-q);
            }
    }
}
