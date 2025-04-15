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
    private int count;

    // construct an empty deque
    @SuppressWarnings("unchecked")
    public Deque() {
        arr = (Item[]) new Object[1];
        head = 0;
        tail = -1;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return count == 0;
    }

    // return the number of items on the deque
    public int size() {
        return count;
    }

    public void resize(int capacity) {
        @SuppressWarnings("unchecked")
        Item[] new_arr = (Item[]) new Object[capacity];
        int pointer = head;
        for (int i = 0; i < size(); i++) {
            new_arr[i] = arr[pointer];
            pointer = (pointer + 1) % arr.length;
        }
        arr = new_arr;
        head = 0;
        tail = size() - 1;
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
        count++;
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
        count++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = arr[head];
        arr[head] = null;
        head = (head + 1 + arr.length) % arr.length;
        count--;
        if (count <= arr.length / 4) resize(arr.length / 2);
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = arr[tail];
        arr[tail] = null;
        tail = (tail - 1 + arr.length) % arr.length;
        count--;
        if (count <= arr.length / 4) resize(arr.length / 2);
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    public class ArrayIterator implements Iterator<Item> {
        private int i = 0;

        public boolean hasNext() {
            return i < count;
        }

        public Item next() {
            return arr[(head + i++) % arr.length];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        while (!StdIn.isEmpty()) {
            deque.addLast(StdIn.readInt());
        }
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(999);
        deque.addFirst(212);
        deque.addFirst(-2);
        deque.addLast(99);
        deque.addLast(20);

        StdOut.printf("The new deque after appending is:\n");
        for (Integer item : deque) {
            StdOut.print(item + "\t");
        }

        int a = deque.removeFirst();
        int b = deque.removeLast();
        StdOut.printf("\nThe first and last items deleted are: %d and %d\n", a, b);
        StdOut.printf("The new deque after deletion is:\n");
        for (Integer item : deque) {
            StdOut.print(item + "\t");
        }
        StdOut.print("\n");
    }
}