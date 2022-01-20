package de.veenix.jtest.InnerOuter;

public class OtherOuterClass {

    public int number = 15;

    public void say() {
        System.out.println("Hey! <3 " + number);
    }

    public static class InnerClass {

        public void hey() {
            //System.out.println("Hey! " + number);
        }

    }

    public interface Interface {

    }

}
