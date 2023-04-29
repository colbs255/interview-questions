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
        } else if (sizeIsEven()) {
            middle = middle.prev;
        }
    }

    public void pushMiddle(T value) {
        insertBefore(value, middle);

        if (size == 1) {
            middle = front.next;
        } else if (sizeIsEven()) {
            middle = middle.prev;
        }
    }

    public void pushBack(T value) {
        insertBefore(value, back);

        if (size == 1) {
            middle = front.next;
        } else if (sizeIsOdd()) {
            middle = middle.next;
        }
    }

    public T popFront() {
        var value = remove(front.next);
        if (size == 0) {
            middle = back;
        } else if (sizeIsEven()) {
            middle = middle.next;
        }
        return value;
    }

    public T popMiddle() {
        var value = remove(middle);
        if (size == 0) {
            middle = back;
        } else if (sizeIsEven()) {
            middle = middle.prev;
        } else if (sizeIsOdd()) {
            middle = middle.next;
        }
        return value;
    }

    public T popBack() {
        var value = remove(back.prev);
        if (size == 0) {
            middle = back;
        } else if (sizeIsOdd()) {
            middle = middle.prev;
        }
        return value;
    }

    private void insertBefore(T value, Node<T> node) {
        var nodeToInsert = new Node<T>(value);
        nodeToInsert.prev = node.prev;
        nodeToInsert.next = node;

        node.prev.next = nodeToInsert;
        node.prev = nodeToInsert;
        size++;
    }

    private T remove(Node<T> node) {
        if (size == 0) {
            throw new IllegalStateException("Cannot remove from empty list");
        }
        var value = node.value;
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
        return value;
    }

    private boolean sizeIsOdd() {
        return size % 2 != 0;
    }

    private boolean sizeIsEven() {
        return size % 2 == 0;
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

        assertThat(q.popMiddle()).isEqualTo(9);
        assertThat(q.asList()).isEqualTo(List.of(1, 5, 6, 8, 10, 10, 5, 1));

        assertThat(q.popMiddle()).isEqualTo(8);
        assertThat(q.asList()).isEqualTo(List.of(1, 5, 6, 10, 10, 5, 1));

        assertThat(q.popMiddle()).isEqualTo(10);
        assertThat(q.asList()).isEqualTo(List.of(1, 5, 6, 10, 5, 1));

        assertThat(q.popFront()).isEqualTo(1);
        assertThat(q.asList()).isEqualTo(List.of(5, 6, 10, 5, 1));

        assertThat(q.popBack()).isEqualTo(1);
        assertThat(q.asList()).isEqualTo(List.of(5, 6, 10, 5));

        assertThat(q.popFront()).isEqualTo(5);
        assertThat(q.asList()).isEqualTo(List.of(6, 10, 5));

        assertThat(q.popBack()).isEqualTo(5);
        assertThat(q.asList()).isEqualTo(List.of(6, 10));

        assertThat(q.popFront()).isEqualTo(6);
        assertThat(q.asList()).isEqualTo(List.of(10));

        assertThat(q.popBack()).isEqualTo(10);
        assertThat(q.asList()).isEqualTo(List.of());
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
