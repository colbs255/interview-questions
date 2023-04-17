import java.util.*;

class MiddleQueue<T> {

    private Node front;
    private Node middle;
    private Node back;

    private int size;

    public MiddleQueue() {
        this.front = new Node<>(null);
        this.back = new Node<>(null);
        this.middle = back;
        this.size = 0;
    }

    public void pushFront(T item) {
        var nodeToInsert = new Node<>(item);
        nodeToInsert.prev = front;
        nodeToInsert.next = front.next;
        front.next = nodeToInsert;
        size++;
        
        if (size % 2 != 0) {
            middle = middle.prev;
        }
    }

    public void pushMiddle(T item) {

    }

    public void pushBack(T item) {
        var nodeToInsert = new Node<>(item);
        nodeToInsert.prev = back.prev;
        nodeToInsert.next = back;
        back.prev = nodeToInsert;
    }

    public T popFront(T item) {
        return null;
    }

    public T popMiddle(T item) {
        return null;
    }

    public T popBack(T item) {
        return null;
    }

    private static class Node<T> {
        T value;
        Node prev;
        Node next;

        Node(T value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "{ value: " + value
                + ", prev: " + prev
                + ", next: " + next
                + " }";
        }
    }

    @Override
    public String toString() {
        var builder = new StringBuilder();
        var node = front.next;
        while (node != null) {
            builder.append(node.value + ", ");
            node = node.next;
        }

        return builder.toString();
    }

    public static void main(String[] args) {
        var q = new MiddleQueue<Integer>();
        q.pushFront(3);
        q.pushFront(2);
        q.pushFront(1);
        System.out.println(q);
    }
}
