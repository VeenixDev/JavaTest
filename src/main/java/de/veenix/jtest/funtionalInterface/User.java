package de.veenix.jtest.funtionalInterface;

public class User {

    private String username;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void uppercaseUsername() {
        username = username.toUpperCase();
    }

}
