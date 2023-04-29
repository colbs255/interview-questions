import java.util.*;
class MiddleQueue<T> {

    private final Node<T> front;
    private final Node<T> back;

    private Node<T> middle;

    private int size;

    public MiddleQueue() {
        this.front = new Node<>(null);
        this.back = new Node<>(null);
        this.middle = back;
        this.front.next = back;
        this.back.prev = front;
        this.size = 0;
    }

    public void pushFront(T value) {
        insertBefore(value, front.next);

        if (size == 1) {
            middle = front.next;
        } else if (size % 2 == 0) {
            middle = middle.prev;
        }
    }

    public void pushMiddle(T value) {
        insertBefore(value, middle);

        if (size == 1) {
            middle = front.next;
        } else if (size % 2 == 0) {
            middle = middle.prev;
        }
    }

    public void pushBack(T value) {
        insertBefore(value, back);

        if (size == 1) {
            middle = front.next;
        } else if (size % 2 != 0) {
            middle = middle.next;
        }
    }

    private void insertBefore(T value, Node<T> node) {
        var nodeToInsert = new Node<T>(value);
        nodeToInsert.prev = node.prev;
        nodeToInsert.next = node;

        node.prev.next = nodeToInsert;
        node.prev = nodeToInsert;
        size++;
    }

    public T popFront() {
        var value = remove(front.next);
        if (size == 0) {
            middle = back;
        } else if (size % 2 == 0) {
            middle = middle.next;
        }
        return value;
    }

    public T popMiddle() {
        var value = remove(middle);
        // TODO: mutate middle
        return value;
    }

    public T popBack() {
        var value = remove(back.prev);
        // TODO: mutate middle
        if (size == 0) {
            middle = back;
        } else if (size % 2 != 0) {
            middle = middle.prev;
        }
        return value;
    }

    private T remove(Node<T> node) {
        var value = node.value;
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
        return value;
    }

    public List<T> asList() {
        var result = new ArrayList<T>();
        var node = front.next;
        while (node != back) {
            result.add(node.value);
            node = node.next;
        }
        return result;
    }

    private static class Node<T> {
        T value;
        Node<T> prev;
        Node<T> next;

        Node(T value) {
            this.value = value;
        }
    }


    public static void main(String[] args) {
        var q = new MiddleQueue<Integer>();
        q.pushFront(10);
        q.pushFront(5);
        q.pushFront(1);
        assertThat(q.asList()).isEqualTo(List.of(1, 5, 10));

        q.pushBack(10);
        q.pushBack(5);
        q.pushBack(1);
        assertThat(q.asList()).isEqualTo(List.of(1, 5, 10, 10, 5, 1));

        q.pushMiddle(6);
        q.pushMiddle(9);
        q.pushMiddle(8);
        assertThat(q.asList()).isEqualTo(List.of(1, 5, 6, 8, 9, 10, 10, 5, 1));
        System.out.println(q.asList());
        q.popFront();
        q.popFront();
        q.popFront();
        System.out.println(q.asList());
    }

    interface Validator<T> {
        public void isEqualTo(T other);
    }

    private static <T> Validator<T> assertThat(T actual) {
        return expected -> {
            if (!actual.equals(expected)) {
                throw new RuntimeException(String.format("Expected: %s but got %s", expected, actual));
            }
        };
    }
}
