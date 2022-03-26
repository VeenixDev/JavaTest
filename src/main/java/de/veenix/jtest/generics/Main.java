package de.veenix.jtest.generics;

public class Main {

    public static void main(String[] args) {
        LinkedList<String> lList = new LinkedList<>();
        LinkedList<Integer> ilList = new LinkedList<>();

        ilList.addToStart(3);
        ilList.addToStart(33);
        ilList.addToEnd(55);

        lList.addToEnd("Mid");
        lList.addToEnd("Last");
        lList.addToStart("Start");

        Node<Integer> node = ilList.getNode();

        if(node == null) {
            System.out.println("empty list");
            return;
        }

        do {
            System.out.println(node.getValue());

            node = node.getNextNode();
        } while(node != null);
    }

}
