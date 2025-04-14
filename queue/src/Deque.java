/* *****************************************************************************
 *  Name:              Biplav Poudel
 *  Coursera User ID:  https://www.coursera.org/learner/biplavpoudel
 *  Last modified:     14 Apr, 2025
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Item[] arr;
    private int head;        // let's use circular array with no fixed ends
    private int tail;

    // construct an empty deque
    @SuppressWarnings("unchecked")
    public Deque(int N) {
        arr = (Item[]) new Object[N];
    }

    // is the deque empty?
    public boolean isEmpty() {
        return head == tail && arr[head] == null;
    }

    // return the number of items on the deque
    public int size() {
        return arr.length;
    }

    public void resize(int capacity) {
        @SuppressWarnings("unchecked")
        Item[] new_arr = (Item[]) new Object[capacity];
        for (int i = 0; i < size(); i++) {
            new_arr[i] = arr[i];
        }
        arr = new_arr;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        // if array full, resize first
        if ((tail + 1) % arr.length == head % arr.length) resize(arr.length * 2);
        // then shift head to the left and add new item to it
        head = (head - 1 + arr.length) % arr.length;
        arr[head] = item;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        // if array full, resize first
        if ((tail + 1 + arr.length) % arr.length == head % arr.length) resize(arr.length * 2);
        // then shift head to the left and add new item to it
        tail = (tail + 1 + arr.length) % arr.length;
        arr[tail] = item;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = arr[head];
        head = (head + 1 + arr.length) % arr.length;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = arr[tail];
        tail = (tail - 1 + arr.length) % arr.length;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    public class ArrayIterator implements Iterator<Item> {
        private int i = 0;

        public boolean hasNext() {
            return i < size();
        }

        public Item next() {
            return arr[i++];
        }


        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        int N = StdIn.readInt();
        Deque<Integer> deque = new Deque<>(N);
        while (!StdIn.isEmpty()) {
            deque.addLast(StdIn.readInt());
        }
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addLast(99);
        deque.addLast(20);
        int a = deque.removeFirst();
        int b = deque.removeLast();
        StdOut.printf("the first and last items deleted are: %d and %d", a, b);
        for (Integer item : deque.arr) {
            StdOut.print(item);
        }
    }
}