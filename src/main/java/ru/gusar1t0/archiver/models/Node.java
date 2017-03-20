package ru.gusar1t0.archiver.models;

/**
 * @author Roman Mashenkin
 * @since 20.03.2017
 */
public final class Node {
    private int n;
    private Data data = null;
    private Node left = null;
    private Node right = null;

    public Node(Data data) {
        this.data = data;
        n = data.getCount();
    }

    public Node(Node left, Node right) {
        this.left = left;
        this.right = right;
        n = left.getN() + right.getN();
    }

    public int getN() {
        return n;
    }

    public Data getData() {
        return data;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }
}
