public class ArrayDeque<T> {
    private int size;
    private T[] array;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        size = 0;
        array = (T []) new Object[8];
        nextFirst = 0;
        nextLast = 1;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private boolean isFull() {
        return size == array.length;
    }

    /**
     * Whether to downsize the deque.
     */
    private boolean isSparse() {
        return array.length >= 16 && size < (array.length / 4);
    }

    private int minusOne(int f) {
        return (f - 1 + array.length) % array.length;
    }

    private int plusOne(int l) {
        return (l + 1) % array.length;
    }

    private void resize(int capacity) {
        T[] newArray = (T []) new Object[capacity];
        int oldindex = plusOne(nextFirst);
        for (int i = 0; i < size; i += 1) {
            newArray[i] = array[nextFirst];
            oldindex = plusOne(oldindex);
        }
        array = newArray;
        nextFirst = capacity - 1;
        nextLast = size;
    }
    private void upSize() {
        resize(size * 2);
    }

    private void downSize() {
        resize(size * 4);
    }
    public void addFirst(T x) {
        if (isFull()) {
            upSize();
        }
        array[nextFirst] = x;
        nextFirst = minusOne(nextFirst);
        size += 1;
    }

    public void addLast(T x) {
        if (isFull()) {
            upSize();
        }
        array[nextLast] = x;
        nextLast = plusOne(nextLast);
        size += 1;
    }

    public T removeFirst() {
        if (isSparse()) {
            downSize();
        }
        nextFirst = plusOne(nextFirst);
        T toRemove = array[nextFirst];
        array[nextFirst] = null;
        if (!isEmpty()) {
            size -= 1;
        }
        return toRemove;
    }

    public T removeLast() {
        if (isSparse()) {
            downSize();
        }
        nextLast = minusOne(nextLast);
        T toRemove = array[nextLast];
        array[nextLast] = null;
        if (!isEmpty()) {
            size -= 1;
        }
        return toRemove;
    }

    public T get(int index) {
        if (index >= size) {
            return null;
        }
        int ptr = plusOne(nextFirst);
        return array[(ptr + index) % array.length];
    }

    public void printDeque() {
        int ptr = nextFirst;
        while (ptr != nextLast) {
            System.out.println(array[ptr]);
            ptr = minusOne(ptr);
        }
    }
}
