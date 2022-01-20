package de.veenix.jtest.InnerOuter;

public class Main {

    public static void main(String[] args) {
        OuterClass outer = new OuterClass();
        outer.say();
        OuterClass.InnerClass inner = outer.new InnerClass();
        inner.hey();
    }

}
