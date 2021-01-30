public class LinkedListDeque<T> {
    private int size;
    private final DeNode sentinel;

    private class DeNode {
        private T item;
        private DeNode prev;
        private DeNode next;
    }

    public LinkedListDeque() {
        sentinel = new DeNode();
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    public void addFirst(T item) {
        size += 1;
        DeNode first = new DeNode();
        first.item = item;
        first.prev = sentinel;
        first.next = sentinel.next;
        sentinel.next.prev = first;
        sentinel.next = first;
    }

    public void addLast(T item) {
        size += 1;
        DeNode last = new DeNode();
        last.item = item;
        last.prev = sentinel.prev;
        last.next = sentinel;
        sentinel.prev.next = last;
        sentinel.prev = last;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        DeNode i = sentinel.next;
        while (i != sentinel) {
            System.out.println(i.item + " ");
            i = i.next;
        }
    }

    public T removeFirst() {
        if (this.isEmpty()) {
            return null;
        }
        size -= 1;
        DeNode first = sentinel.next;
        sentinel.next = first.next;
        first.next.prev = sentinel;
        return first.item;
    }

    public T removeLast() {
        if (this.isEmpty()) {
            return null;
        }
        size -= 1;
        DeNode last = sentinel.prev;
        last.prev.next = sentinel;
        sentinel.prev = last.prev;
        return last.item;
    }

    public T get(int index) {
        if (index > size - 1) {
            return null;
        }
        DeNode current = sentinel.next;
        while (index > 0) {
            current = current.next;
            index -= 1;
        }
        return current.item;
    }

    private T getRecursiveHelper(int index, DeNode curr) {
        if (index == 0) {
            return curr.item;
        }
        return getRecursiveHelper(index - 1, curr.next);
    }

    public T getRecursive(int index) {
        if (index > size - 1 || index < 0) {
            return null;
        }
        return getRecursiveHelper(index, sentinel.next);
    }
}
