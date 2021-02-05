public class ArrayDeque<T> implements Deque<T>{
    private final int INITIAL_SIZE = 8;
    private final double RESIZE_FACTOR = 2.0;
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

    private int calcIndex(int curr, int offset) {
        int index = curr + offset + a.length;
        return index % a.length;
    }

    private void resize(double factor) {
        T[] tmp = (T[]) new Object[(int) (a.length * factor)];
        for (int i = 0; i < size; i++) {
            tmp[i] = a[calcIndex(i, front + 1)];
        }
        a = tmp;
        front = a.length - 1;
        rear = size;
    }

    @Override
    public void addFirst(T item) {
        if (size == a.length) {
            resize(RESIZE_FACTOR);
        }
        size += 1;
        a[front] = item;
        front = calcIndex(front, -1);
    }

    @Override
    public void addLast(T item) {
        if (size == a.length) {
            resize(RESIZE_FACTOR);
        }
        size += 1;
        a[rear] = item;
        rear = calcIndex(rear, 1);
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(get(i) + " ");
        }
    }

    @Override
    public T removeFirst() {
        if (this.isEmpty()) {
            return null;
        }
        front = calcIndex(front, 1);
        size -= 1;
        T tmp = a[front]; // this is needed in case of resize
        a[front] = null;
        if (a.length >= MIN_SIZE && size * 4 < a.length) {
            resize(1 / RESIZE_FACTOR);
        }
        return tmp;
    }

    @Override
    public T removeLast() {
        if (this.isEmpty()) {
            return null;
        }
        rear = calcIndex(rear, -1);
        size -= 1;
        T tmp = a[rear]; // this is needed in case of resize
        a[rear] = null;
        if (a.length >= MIN_SIZE && size * 4 < a.length) {
            resize(1 / RESIZE_FACTOR);
        }
        return tmp;
    }

    @Override
    public T get(int index) {
        if (index > size - 1 || index < 0) {
            return null;
        }
        return a[calcIndex(index, front + 1)];
    }
}
