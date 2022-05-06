package model;

public class Node <T> {

    public Node<T> prev;
    public T data;
    public Node<T> next;

    public Node(T data) {
        this.prev = null;
        this.data = data;
        this.next = null;

    }

    public Node(Node<T> prev, T data, Node<T> next) {
        this.prev = prev;
        this.data = data;
        this.next = next;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                '}';
    }
}

