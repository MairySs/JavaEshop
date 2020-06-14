package com.company;

public class Buyer extends User {
    private int bonus;
    private String buyerCategory;       //bronze,silver,gold
    ShoppingCart cart= new ShoppingCart();

    public Buyer(String name,String email) {
        super (name,email);
        bonus=0;
        buyerCategory="BRONZE";
        this.cart =cart;
    }

    public int getBonus() {
        return bonus;
    }

   public String getBuyerCategory() {
        return buyerCategory;
    }

    public ShoppingCart getCart() {
        return cart;
    }

    public void awardBonus(int bonus){
        this.bonus+=bonus;
        setBuyerCategory(this.bonus);
    }

    public void setBuyerCategory(int bonus){

        if (bonus>=100 && bonus<200){
            buyerCategory="SILVER"; //έχει 50% έκπτωση στα μεταφορικά
        }
        else if (bonus>=200){
            buyerCategory="GOLD"; //δεν πληρώνει μεταφορικά
        }
    }

    public void placeOrder(Item item, int quantity){ //προσθέτει στο καλάθι καλωντας μεθοδους της shoppingcart
        try {
            getCart().addItemOrdered(item, quantity);
             } catch (NotEnoughStockException e) {
            System.out.println(e);
        }
    }

}
