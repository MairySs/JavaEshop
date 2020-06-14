package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Eshop {
    private String name;
    private Owner owner;
    private static ArrayList<Item> itemsList = new ArrayList<Item>();
    private static ArrayList<Buyer> buyersList = new ArrayList<Buyer>(); //static αφού η λίστα είναι ίδια για όλα τα στιγμιότυπα/αντικείμενα
    Scanner s = new Scanner(System.in);
    Scanner ss = new Scanner(System.in);

    public static ArrayList<Buyer> getBuyersList() {
        return buyersList;
    }

    public static ArrayList<Item> getItemsList() {
        return itemsList;
    }

    public void addItem() throws ItemExistsException, NoSuchOptionException { // προσθέτει ένα προϊόν στο κατάστημα. Γίνεται χειρισμός εξαίρεσης αν υπάρχει ήδη.
            //η εκφώνηση δεν αναφέρει που χρησιμοποιείται οπότε την έβαλα ως 6η επιλογή του Owner. Δεν γίνεται να υπάρχουν προιόντα με το ίδιο ID
        int x;

        System.out.println("What do you want to add");
        System.out.println(" 1.Pen \n 2.Pencil \n 3.Notebook \n 4.Paper ");
        System.out.println("Type the number of your choice:");
        x = s.nextInt();

        System.out.println("Enter a name for your item:");
        String name = ss.nextLine();
        System.out.println("Enter a description for your item:");
        String description = ss.nextLine();
        System.out.println("Enter a price for your item:");
        double price = s.nextDouble();
        System.out.println("Enter a stock number for your item:");
        int stock = s.nextInt();

       System.out.println("Enter an ID for your item: (The last id is: " + itemsList.size() + ")");

        int id = s.nextInt();

        for (Item it : itemsList) {
            if (name.equalsIgnoreCase(it.getName()) || id == it.getId()) {
                throw new ItemExistsException();
            }
        }
        switch (x) {
            case 1: //pen
                System.out.println("Enter a color for your pen:");
                String color = ss.nextLine();
                System.out.println("Enter a tip size for your pen:");
                double tipSize = s.nextDouble();
                itemsList.add(new Pen(name, description, price, stock, id, color, tipSize));
                System.out.println("Pen added");
                break;
            case 2: //Pencil
                System.out.println("Enter a tip size for your pencil:");
                tipSize = s.nextDouble();
                System.out.println("Enter a type for your pencil {H, B, HB} :"); //EXCEPTION
                String type = ss.nextLine();
                itemsList.add(new Pencil(name, description, price, stock, id, tipSize, type));
                System.out.println("Pencil added");
                break;
            case 3: //Notebook
                System.out.println("Enter sections number for your notebook:");
                int sections = s.nextInt();
                itemsList.add(new Notebook(name, description, price, stock, id, sections));
                System.out.println("Notebook added");
                break;
            case 4: //Paper
                System.out.println("Enter the weight of the paper:");
                int weight = s.nextInt();
                System.out.println("Enter number of pages:");
                int pages = s.nextInt();
                itemsList.add(new Paper(name, description, price, stock, id, weight, pages));
                System.out.println("Paper added");
                break;
            default: throw new NoSuchOptionException();
        }
    }

    public static void initialize() { //Αρχικά δεδομένα
        itemsList.add(new Pen("pen1", "it is a pen", 2.50, 10, 1, "blue", 0.7));
        itemsList.add(new Pen("pen2", "it is the second pen", 4.50, 4, 2, "black", 0.5));
        itemsList.add(new Pen("pen3", "it is the third pen", 5.50, 15, 3, "red", 1.0));

        itemsList.add(new Pencil("pencil1", "it is a pencil1", 1.00, 13, 4, 0.5, "HB"));
        itemsList.add(new Pencil("pencil2", "it is a pencil2", 2.00, 10, 5, 0.7, "B"));
        itemsList.add(new Pencil("pencil3", "it is a pencil3", 3.00, 7, 6, 0.9, "H"));

        itemsList.add(new Notebook("notebook1", "it is a notebook1", 5.00, 2, 7, 3));
        itemsList.add(new Notebook("notebook2", "it is a notebook2", 7.00, 4, 8, 2));
        itemsList.add(new Notebook("notebook3", "it is a notebook3", 6.50, 12, 9, 4));

        itemsList.add(new Paper("paper1", "it is paper1", 1.00, 3, 10, 500, 100));
        itemsList.add(new Paper("paper2", "it is paper2", 5.00, 14, 11, 700, 200));
        itemsList.add(new Paper("paper3", "it is paper3", 10.00, 5, 12, 800, 500));

        buyersList.add(new Buyer("A", "ab"));
        buyersList.add(new Buyer("B", "bc"));

        Buyer buyer = buyersList.get(0);
        buyer.awardBonus(198);



        Eshop.getBuyersList().get(0).placeOrder(Eshop.getItemsList().get(0), 2);
        Eshop.getBuyersList().get(0).placeOrder(Eshop.getItemsList().get(3), 7);
        Eshop.getBuyersList().get(0).placeOrder(Eshop.getItemsList().get(5), 2);
        Eshop.getBuyersList().get(0).placeOrder(Eshop.getItemsList().get(8), 5);

        Eshop.getBuyersList().get(1).placeOrder(Eshop.getItemsList().get(1), 3);
        Eshop.getBuyersList().get(1).placeOrder(Eshop.getItemsList().get(9), 2);
        Eshop.getBuyersList().get(1).placeOrder(Eshop.getItemsList().get(10), 7);
        Eshop.getBuyersList().get(1).placeOrder(Eshop.getItemsList().get(3), 3);

    }

    private Item getItemById(int id) throws ItemNotFoundException {
        // ανακτά ένα Item από την itemsList με βάση τον κωδικό του, δεν αναφέρεται από την εκφώνηση αν χρησιμοποιείται κάπου, οπότε έχει μείνει ως Private
        Item item = null;

        for (Item it : itemsList) {
            if (id == it.getId()) {
                item = it;
            }
        }
        if (item != null) {
            return item;
        } else throw new ItemNotFoundException();
    }

    private void removeItem(Item item) { //αφαιρεί ένα προϊόν, δεν αναφέρεται από την εκφώνηση που χρησιμοποιείται οπότε έμεινε ως Private
        itemsList.remove(item);
    }

    public static void addBuyer(String name, String email) throws BuyerAlreadyExistsException { //προσθέτει έναν υποψήφιο πελάτη στο κατάστημα. Γίνεται χειρισμός εξαίρεσης, αν υπάρχει ήδη με το ίδιο όνομα ή εμαιλ
        for (Buyer b : buyersList) {
            if (name.equalsIgnoreCase(b.getName()) || email.equalsIgnoreCase(b.getEmail())) {
                throw new BuyerAlreadyExistsException();
            }
        }
        buyersList.add(new Buyer(name, email));
        System.out.println("Successful Registration, happy shopping " + name + " !");
    }

    public void removeBuyer(Buyer buyer) { // αφαιρεί έναν πελάτη από το κατάστημα
        buyersList.remove(buyer);

        System.out.println("Customer deleted");

    }

    public static void updateItemStock(int id, int stock) throws NotEnoughStockException { //τροποποιεί τη διαθέσιμη ποσότητα ενός προϊόντος
        if (stock < 0) {
            throw new NotEnoughStockException("Stock value can not be negative");}
        for (Item it : itemsList) {
            if (id == it.getId()) {
                it.setStock(stock);
            }
        }
    }

    public void showCategories() { // Εμφανίζει τις υπάρχουσες κλάσεις των προϊόντων
        int pen = 0;
        int pencil = 0;
        int notebook = 0;
        int paper = 0;

        for (int i = 0; i < itemsList.size(); i++) {
            if (itemsList.get(i) instanceof Pen) {
                pen++;
            }
            if (itemsList.get(i) instanceof Pencil) {
                pencil++;
            }
            if (itemsList.get(i) instanceof Notebook) {
                notebook++;
            }
            if (itemsList.get(i) instanceof Paper) {
                paper++;
            }

        }
        System.out.println("We have products of the following categories: \n 1.Pen (" + pen + ")" + "\n 2.Pencil (" + pencil + ")  \n 3.Notebook (" + notebook + ")" + " \n 4.Paper (" + paper + ")");

    }

    public int showProductsInCategory() throws NoSuchOptionException { //Εμφανίζει λίστα με τα προϊόντα μιας συγκεκριμένης κατηγορίας που επιλέγεται
        System.out.println("Choose the category you want ");
        int x = s.nextInt();

        switch (x) {
            case 1: //pen
                for (int i = 0; i < itemsList.size(); i++) {
                    if (itemsList.get(i) instanceof Pen) {
                        System.out.println(i + 1 + ". " + itemsList.get(i).getName());
                    }
                }
                break;
            case 2: //Pencil
                for (int i = 0; i < itemsList.size(); i++) {
                    if (itemsList.get(i) instanceof Pencil) {
                        System.out.println(i + 1 + ". " + itemsList.get(i).getName());
                    }
                }
                break;
            case 3: //Notebook
                for (int i = 0; i < itemsList.size(); i++) {
                    if (itemsList.get(i) instanceof Notebook) {
                        System.out.println(i + 1 + ". " + itemsList.get(i).getName());
                    }
                }
                break;
            case 4: //Paper
                for (int i = 0; i < itemsList.size(); i++) {
                    if (itemsList.get(i) instanceof Paper) {
                        System.out.println(i + 1 + ". " + itemsList.get(i).getName());
                    }
                }
                break;
            default:
                throw new NoSuchOptionException();
        }
        return x;
    }

    public Item showProduct() throws NoSuchOptionException { // Εμφανίζει τις συνολικές πληροφορίες για ένα προϊόν που επιλέγεται
        System.out.println("Choose the number of the item you want ");
        int x = s.nextInt();
        System.out.println(itemsList.get(x - 1));
        return itemsList.get(x - 1);
    }

    public void checkStatus() { // Εμφανίζει τους πελάτες, τους πόντους και την κατηγορία τους.
        int i=0;
            for (Buyer b : buyersList) {
                i++;
                System.out.println(i + ". " +"Name: " + b.getName() + "  Bonus: " + b.getBonus() + "  Category: " + b.getBuyerCategory());
            }
        }
}

