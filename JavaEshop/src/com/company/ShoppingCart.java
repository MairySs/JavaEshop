package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class ShoppingCart {
    private ArrayList<ItemOrdered> orderList = new ArrayList<ItemOrdered>();



    public void addItemOrdered(Item item, int quantity) throws NotEnoughStockException {
        boolean flag = false;
        if (quantity > item.getStock()) throw new NotEnoughStockException();
        else {
            item.setStock(item.getStock() - quantity);
            flag = true;

            for (ItemOrdered it : orderList) { //ψάχνει εάν υπάρχει ήδη στο cart
                if (item.getId() == it.item.getId()) {
                    it.setQuantity(it.getQuantity() + quantity); //Αν το βρει του αυξάνει τα τεμάχια
                    flag = false;
                }
            }
            if (flag) { //Αν δε το βρει στο cart, το προσθέτει
                orderList.add(new ItemOrdered(item, quantity));
                System.out.println("Item added to cart");
            }
        }
    }

    public void removeItemOrdered(int x, boolean fromclear) { //Αφαιρεί ένα αντικείμενο ItemOrdered από μια θέση της λίστας. Ενημερώνει το stock.
        if (fromclear) { //Εάν καλείται από τη μέθοδο clearcart και άρα θα πρέπει να καθαρίσει όλη τη λίστα και όχι μόνο ένα αντικείμενο αλλά να ενημερώσει και το στοκ
            for (ItemOrdered it:orderList) {
                int a = it.item.getStock();
                int b = it.getQuantity();
                it.item.setStock(it.item.getStock() + it.getQuantity());
            }
            orderList.clear();

        } else{
            ItemOrdered itt = orderList.get(x);
            itt.item.setStock(itt.item.getStock() + itt.getQuantity());
            orderList.remove(x);
            System.out.println("Item deleted");

        }
    }

    public void changeItemOrderedQuantity(int x, int q){ //Για ένα αντικείμενο σε μια θέση της λίστας τροποποιεί το quantity και ενημερώνει το stock (updateItemStock()). Προηγείται έλεγχος αν υπάρχει διαθέσιμο stock.
        ItemOrdered itt = orderList.get(x);
        int id= itt.item.getId();
        int a =itt.getQuantity();

        if (q>a){
            try {
            Eshop.updateItemStock(id,itt.item.getStock()-(q-a));
            itt.setQuantity(itt.getQuantity()+(q-a));
        } catch (NotEnoughStockException e) {
                System.out.println("There is not enough stock");
                return;
        }

        } else if (q<a){
            try {
                Eshop.updateItemStock(id,itt.item.getStock()+(a-q));
                itt.setQuantity(itt.getQuantity()-(a-q));
            } catch (NotEnoughStockException e) {
                System.out.println("There is not enough stock");
                return;

                }
        }

    }
    public void showCart(Buyer buyer) throws EmptyCartException { //Εμφανίζει τα περιεχόμενα του καλαθιού αγορών, δηλαδή τα προϊόντα, τις ποσότητες και τις τιμές τους, το σύνολο και τα μεταφορικά.

            if (orderList.size() != 0) {
                for (int i = 0; i < orderList.size(); i++) {
                    System.out.println("Item " + (i + 1) + ": Name: " + orderList.get(i).getItem().getName() + " | Quantity: " + orderList.get(i).getQuantity() + " | Price: " + orderList.get(i).getItem().getPrice());
                }
                double total = calculateCourierCost(buyer.getBuyerCategory()) + calculateNet();
                System.out.println("Total: " + calculateNet() + "  +  Courier Cost: " + calculateCourierCost(buyer.getBuyerCategory()));
                System.out.println("Your Total Cost will be: " + total);

            } else throw new EmptyCartException();

    }
    public void clearCart(){ //Καλεί τη removeItemOrdered() και αφαιρεί όλα τα αντικείμενα από τη λίστα.
            removeItemOrdered(0,true);
    }

    public void checkout(Buyer buyer){
        /*Καλεί την showCart(). Ζητά επιβεβαίωση για πληρωμή και εφόσον είναι θετική, καλεί την awardBonus() του Buyer και διαγράφει τα περιεχόμενα
           της λίστας (δεν αλλάζει όμως το stock). Οι πόντοι υπολογίζονται σε ποσοστό 10% επί της αξίας της παραγγελίας (χωρίς τα μεταφορικά) και είναι ακέραιος αριθμός.*/

        Scanner s = new Scanner(System.in);
        try {
            showCart(buyer);
        } catch (EmptyCartException e) {
            e.printStackTrace();
        }
        System.out.println("Proceed to payment? y/n");
        String ans = s.nextLine();
        if (ans.equalsIgnoreCase("y")){
            buyer.awardBonus((int)(calculateNet()*0.10));
            orderList.clear();
        }

    }
    private double calculateNet(){ //Επιστρέφει την αξία της παραγγελίας.
        double net=0;
        for (ItemOrdered it:orderList) {
            net += (it.item.getPrice()*it.getQuantity());
        }
        return net;

    }
    private double calculateCourierCost(String category){
        /*Υπολογίζει το κόστος μεταφορικών το οποίο είναι το 2% της αξίας της παραγγελίας, αλλά δεν μπορεί να είναι μικρότερο από 3
        ευρώ. Ελέγχει σε ποια κατηγορία είναι ο πελάτης (Buyer). Αν είναι στην κατηγορία gold, δεν πληρώνει μεταφορικά, ενώ αν είναι στην κατηγορία silver
        έχει 50% έκπτωση στα μεταφορικά, ακόμα κι αν το ποσό που προκύπτει είναι μικρότερο από 3 ευρώ.*/
        double cost = calculateNet()*0.02 ;
        if(category.equals("SILVER")){
            cost=cost/2;
        } else if (category.equals("GOLD")){
            cost=0;
        }else if (cost<3){
            cost=3;
        }
        return cost;
    }

}
