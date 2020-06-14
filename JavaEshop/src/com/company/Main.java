package com.company;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Eshop eshop = new Eshop();
        Eshop.initialize();
        Menu m = new Menu();
        try {
            m.Welcome();
        } catch (NoSuchOptionException e) {
            e.printStackTrace();
        }



    }
}
