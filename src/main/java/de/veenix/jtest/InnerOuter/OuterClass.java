package de.veenix.jtest.InnerOuter;

public class OuterClass {

    public int number = 15;

    public void say() {
        System.out.println("say 'Hey!'~ " + number);
    }

    public class InnerClass {

       public void hey() {
           System.out.println("Hey! " + number);
       }

    }
}
