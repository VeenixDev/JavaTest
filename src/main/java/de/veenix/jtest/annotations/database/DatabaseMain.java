package de.veenix.jtest.annotations.database;

public class DatabaseMain {

    @DBProfile( username = "testusr",
            password = "123456",
            port = 3306)
    private static Database myDatabase = null;

    public static void main(String[] args) {
        if(myDatabase == null) {
            System.out.println("Database is null!!");
        } else {
            System.out.println("Database connected");
        }
    }

}
