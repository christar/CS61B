public class ArrayDeque<T> {
    private final int INITIAL_SIZE = 8;
    private final int RESIZE_FACTOR = 2;
    private final int MIN_SIZE = 16;

    private int size;
    private T[] a;
    private int front;
    private int rear;

    public ArrayDeque() {
        size = 0;
        front = 0;
        rear = 1;
        a = (T[]) new Object[INITIAL_SIZE];
    }

    private void resize(double factor) {
        T[] tmp = (T[]) new Object[(int) (a.length * factor)];
        for (int i = 0; i < size; i++) {
            tmp[i] = a[(i + front + 1) % size];
        }
        a = tmp;
        front = a.length - 1;
        rear = size;
    }

    public void addFirst(T item) {
        if (size == a.length) {
            resize(RESIZE_FACTOR);
        }
        size += 1;
        a[front] = item;
        front = (front + a.length - 1) % a.length;
    }

    public void addLast(T item) {
        if (size == a.length) {
            resize(RESIZE_FACTOR);
        }
        size += 1;
        a[rear] = item;
        rear = (rear + 1) % a.length;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.println(a[(i + front + 1) % a.length] + " ");
        }
    }

    public T removeFirst() {
        if (this.isEmpty()) {
            return null;
        }
        front = (front + 1) % a.length;
        size -= 1;
        T tmp = a[front]; // this is needed in case of resize
        if (a.length >= MIN_SIZE && size * 4 < a.length) {
            resize(0.5);
        }
        return tmp;
    }

    public T removeLast() {
        if (this.isEmpty()) {
            return null;
        }
        rear = (rear + a.length - 1) % a.length;
        size -= 1;
        T tmp = a[rear]; // this is needed in case of resize
        if (a.length >= MIN_SIZE && size * 4 < a.length) {
            resize(0.5);
        }
        return tmp;
    }

    public T get(int index) {
        if (index > size - 1 || index < 0) {
            return null;
        }
        return a[(front + 1 + index) % a.length];
    }
}
