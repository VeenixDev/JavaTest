package de.veenix.jtest.generics;


public class LinkedList<T> {

    private Node<T> startNode;

    public void addToEnd(T value) {
        if(startNode == null) {
            this.startNode = new Node<>(value, null, null);
            return;
        }

        if(startNode.getNextNode() == null) {
            startNode.setNextNode(new Node<>(value, null, startNode));
            return;
        }

        Node<T> node = startNode.getNextNode();
        while(node.getNextNode() != null) {
            node = node.getNextNode();
        }

        node.setNextNode(new Node<>(value, null, node));
    }

    public void addToStart(T value) {
        if(startNode == null) {
            this.startNode = new Node<>(value, null, null);
            return;
        }

        Node<T> node = new Node<>(value, startNode, null);

        startNode.setPreviousNode(node);
        startNode = node;
    }

    public Node<T> getNode() {
        return startNode;
    }
}
