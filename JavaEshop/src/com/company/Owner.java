package com.company;

public class Owner extends User {

    private boolean isAdmin = true;

    public Owner(String name, String email, boolean isAdmin) {
        super(name, email);
        this.isAdmin = isAdmin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
