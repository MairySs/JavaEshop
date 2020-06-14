package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    Scanner s = new Scanner(System.in);
    Scanner ss=new Scanner(System.in);
    private String email;
    private Owner owner = new Owner("Owner A", "oo", true);
    private ArrayList<Buyer> BuyerList = Eshop.getBuyersList();
    private ArrayList<Item> itemsList = Eshop.getItemsList();

    Eshop eshop = new Eshop();


    private Buyer buyer;
   // private ShoppingCart cart;
    private boolean registered=false;

    public void Welcome() throws NoSuchOptionException { //Τα αρχικά login-register

        while (true) {
            registered=false;
            System.out.println("Welcome! \n");
            System.out.println("Please enter your email:");
            email = ss.nextLine();

            //Ψάξε αν το email υπάρχει ήδη στη λίστα πελατών
            for (Buyer buyers : BuyerList) {
                if (email.equalsIgnoreCase(buyers.getEmail())) {
                    registered = true;
                    System.out.println("Email found on buyerslist");
                    buyer = buyers;
                }
            }

            //ψάξε εάν το email είναι του owner και έχει admin rights.
            if (email.equalsIgnoreCase(owner.getEmail()) && owner.isAdmin()) {
                System.out.println("Hello Owner " + owner.getName() + " !");
                System.out.println("Your email is: " + owner.getEmail() + " and you are the admin");
                AdminChoices();

            // εάν ο πελάτης είναι ήδη εγγεγραμμένος
            } else if (registered) {
                System.out.println("Welcome " + buyer.getName() + " !");
                BuyerChoices();

                //εάν δεν είναι εγγεγραμμένος, κανε εγγραφή
            } else {
                System.out.println("Email does not exist. Do you want to register as a new customer? Enter y/n");
                String reg = ss.nextLine();
                if (reg.equalsIgnoreCase("y")) {
                    System.out.println("Please enter your email again ");
                    email = ss.nextLine();
                    System.out.println("Please enter your name ");
                    String name = ss.nextLine();
                    try {
                        Eshop.addBuyer(name, email);

                        for (Buyer buyers : BuyerList) {
                            if (email.equalsIgnoreCase(buyers.getEmail())) {
                                registered = true;
                                System.out.println("Email found on buyerslist");
                                buyer = buyers;
                            }
                        }
                        BuyerChoices();
                    } catch (BuyerAlreadyExistsException e) {
                        System.out.println("Email is already Registered. Try again.");
                        continue;
                    }
                } else {
                    System.out.println("Bye!");
                    return;
                }
            }
        }
    }

    private void BrowseStore(){ //Η επιλογή browseStore του buyer

        Scanner sc = new Scanner(System.in);
        System.out.println("---------ESHOP---------");
        eshop.showCategories();
        try {
            eshop.showProductsInCategory();
            Item item2=eshop.showProduct();
            System.out.println("Do you want to add this item to your cart? y/n");
            String ans = sc.nextLine();

            if (ans.equalsIgnoreCase("y")) {
                System.out.println("How many do you want?");
                int q=sc.nextInt();
                buyer.placeOrder(item2,q);
            }
            BuyerChoices();

        } catch (NoSuchOptionException e) {
            e.printStackTrace();
        }
    }

    private void BuyerChoices() throws NoSuchOptionException { //Το μενού του buyer
        System.out.println("Your email is: " + buyer.getEmail() + ", you have " + buyer.getBonus() + " bonus and you are a " + buyer.getBuyerCategory() + " buyer");
        System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
        System.out.println("Please enter the number of your choice: ");
        System.out.println(" 1. Browse Store \n 2. View Cart \n 3.Checkout \n 4. Back \n 5. Log out \n 6. Exit ");
        while (true) {
            int w = s.nextInt();
            switch (w){
                case 1: //Browse Store
                    BrowseStore();
                    break;
                case 2: //View Cart
                    try {
                        buyer.getCart().showCart(buyer);
                    } catch (EmptyCartException e) {
                        System.out.println(e);
                        BuyerChoices();
                    }
                    ViewCart();
                    BuyerChoices();
                    break;
                case 3: //Checkout
                    buyer.getCart().checkout(buyer);
                    BuyerChoices();
                    break;
                case 4: //Back
                    return;
                case 5: //Log out
                    System.out.println("Do you wish to login as another user? [y/n]");
                    String a = ss.nextLine();
                    if (a.equalsIgnoreCase("y")){
                        return;
                        }else {
                        System.out.println("Bye!");
                        System.exit(0);}
                    break;
                case 6: //Exit
                    System.out.println("Bye!");
                    System.exit(0);

                default:
                    throw new NoSuchOptionException();

            }
        }
    }

    private void AdminChoices() throws NoSuchOptionException { //Το μενού του owner
        while (true) {
            System.out.println("Please enter the number of your choice: ");
            System.out.println(" 1. Browse Store \n 2. Check Status \n 3. Back \n 4. Log out \n 5. Exit ");
            System.out.println("Enter 6 if you want to add new items to your shop");
            int x = s.nextInt();

            switch (x){
                case 1: //Browse Store
                    BrowseOwner();
                    break;
                case 2: //Check Status
                    eshop.checkStatus();
                    System.out.println("Do you want to select a customer? [y/n]");
                    String y=ss.nextLine();
                    if (y.equalsIgnoreCase("y")){
                        System.out.println("Type the number of the customer to see his cart");
                        int c=s.nextInt()-1;
                        Buyer b = BuyerList.get(c);
                        try {
                            b.getCart().showCart(b);
                        } catch (EmptyCartException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Do you want to delete this customer?");
                        String u=ss.nextLine();
                        if (u.equalsIgnoreCase("y")){
                            eshop.removeBuyer(b);
                            b.getCart().clearCart();
                        }
                    }
                    break;
                case 3: //Back
                    return;
                case 4: //Log out
                    System.out.println("Do you wish to login as another user? [y/n]");
                    String a = ss.nextLine();
                    if (a.equalsIgnoreCase("y")){
                        return;
                    }else {
                        System.out.println("Bye!");
                        System.exit(0);}
                    break;
                case 5: //Exit
                    System.out.println("Bye!");
                    System.exit(0);
                case 6: //Add new items
                    try {
                        eshop.addItem();
                    } catch (ItemExistsException e) {
                        System.out.println(e);
                    }
                    break;

                default: throw new NoSuchOptionException();
            }
        }
    }

    private void ViewCart() throws NoSuchOptionException { //Το viewCart για τον buyer
        System.out.println("-----------------------");
        System.out.println("What do you want to do?");
        System.out.println("1. Choose an item from your cart \n2. Delete everything from cart \n3. Checkout \n4. Go back");
        int c = s.nextInt();

        if (c==1) {
            System.out.println("1. Delete an item \n2. Modify an item \n3. Go back");
            while (true) {
                int x = s.nextInt();
                switch (x) {
                    case 1: //Delete
                        System.out.println("Which item do you want to delete?");
                        int z = s.nextInt() - 1;
                        buyer.getCart().removeItemOrdered(z, false);
                        return;
                    case 2: //Modify
                        System.out.println("Which item do you want to modify?");
                        z = s.nextInt() - 1;
                        System.out.println("New quantity of the item: ?");
                        int q = s.nextInt();
                        buyer.getCart().changeItemOrderedQuantity(z, q);
                        return;
                    case 3: //Back
                        return;
                    default:
                        throw new NoSuchOptionException();
                }

            }
        }

        else if (c == 2) {
                buyer.getCart().clearCart();
                System.out.println("Cart cleared");
            } else if (c == 3) {
                buyer.getCart().checkout(buyer);

            } else if (c == 4) {
                return;

            } else throw new NoSuchOptionException();
        }

    private void BrowseOwner(){ //Η επιλογή του browse για τον owner

        Scanner sc = new Scanner(System.in);
        System.out.println("---------ESHOP---------");
        eshop.showCategories();
        try {
            eshop.showProductsInCategory();
            Item item2=eshop.showProduct();
            System.out.println("Do you want to modify this product? y/n");
            String ans = sc.nextLine();

            if (ans.equalsIgnoreCase("y")) {
                System.out.println("Enter new item stock?");
                int q=sc.nextInt();
                int x = itemsList.indexOf(item2)+1;
                try {
                    Eshop.updateItemStock(x,q);

                } catch (NotEnoughStockException e) {
                    e.printStackTrace();
                }

            }
            AdminChoices();

        } catch (NoSuchOptionException e) {
            e.printStackTrace();
        }
    }


}











