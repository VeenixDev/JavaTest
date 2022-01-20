package de.veenix.jtest.funtionalInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Main {

    static int number = 0;

    public static void main(String[] args) {
        Float result = operateWithTwo(f -> Float.parseFloat(String.valueOf(Math.pow(f, 4))));

        System.out.println("Operate with two: " + result);

        ArrayList<User> users = new ArrayList<>();
        users.add(new User("vfsd"));
        users.add(new User("daWdaw"));
        users.add(new User("ghg"));
        users.add(new User("VeenixDev"));

        uppercaseAllUsernames(users);

        System.out.println("Uppercase usernames:");
        for (User u : users) {
            System.out.println(u.getUsername());
        }

        System.out.println("Before consumer: " + number);
        consume5(i -> number += i);
        System.out.println("After consumer: " + number);

        System.out.println("Supplier: " + supplier(() -> number));
    }

    public static Float operateWithTwo(Function<Float, Float> operation) {
        return operation.apply(2f);
    }

    public static void uppercaseAllUsernames(List<User> users) {
        users.forEach(User::uppercaseUsername);
    }

    public static void consume5(Consumer<Integer> consumer) {
        consumer.accept(5);
    }

    public static Integer supplier(Supplier<Integer> supplier) {
        return supplier.get();
    }
}
