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
    // @SuppressWarnings("unchecked")
    public Deque() {
        arr = (Item[]) new Object[1];
        head = 0;
        tail = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return count == 0;
    }

    // return the number of items on the deque
    public int size() {
        return count;
    }

    private void resize(int capacity) {
        // @SuppressWarnings("unchecked")
        Item[] newArr = (Item[]) new Object[capacity];
        int oldLength = size();
        for (int i = 0; i < oldLength; i++) {
            newArr[i] = arr[(head + i) % arr.length];
        }
        arr = newArr;
        head = 0;
        tail = oldLength - 1;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        // if array full, resize first
        if (count == arr.length)
            resize(arr.length * 2);
        // then shift head to the left and add new item to it

        // if empty array, when an element is added, tail = head = 0
        if (isEmpty()) {
            head = 0;
            tail = 0;
        }
        else {
            head = (head - 1 + arr.length) % arr.length;
        }
        arr[head] = item;
        count++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        // if array full, resize first
        if (count > 0 && count == arr.length) resize(arr.length * 2);
        // then shift head to the left and add new item to it

        if (isEmpty()) tail = 0;
        else tail = (tail + 1 + arr.length) % arr.length;

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
        count--;
        if (isEmpty()) {
            head = 0;
            tail = -1;
        }
        else head = (head + 1 + arr.length) % arr.length;
        if (count > 0 && count <= arr.length / 4) resize(arr.length / 2);
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = arr[tail];
        arr[tail] = null;
        count--;
        if (isEmpty()) {
            tail = -1;
            head = 0;
        }
        else tail = (tail - 1 + arr.length) % arr.length;
        if (count > 0 && count <= arr.length / 4) resize(arr.length / 2);
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int i = 0;

        public boolean hasNext() {
            return i < count;
        }

        public Item next() {
            if (i >= count) throw new NoSuchElementException();
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
        StdOut.printf("The initial deque is:\n");
        for (Integer item : deque) {
            StdOut.print(item + "\t");
        }
        deque.isEmpty();        //==> true
        deque.addFirst(2);
        deque.removeLast();      //==> 2
        deque.addFirst(4);
        deque.addFirst(5);
        deque.removeLast();      //==> 4
        deque.removeLast();     //==> 5
        deque.addFirst(8);
        StdOut.print(deque.removeLast());

        // deque.addFirst(1);
        // deque.addFirst(2);
        // StdOut.print(deque.removeFirst());     //==> 2
        // StdOut.print(deque.isEmpty());         //==> false
        // StdOut.print(deque.removeLast());      //==> 1
        // deque.addFirst(6);
        // deque.addLast(7);
        // deque.size();            //==> 2
        // deque.size();            //==> 2
        // StdOut.printf("\ndigit is:%d", deque.removeFirst());

        StdOut.printf("\nThe new deque is:\n");
        for (Integer item : deque) {
            StdOut.print(item + "\t");
        }

        // StdOut.printf("\nThe new deque is:\n");
        // for (Integer item : deque) {
        //     StdOut.print(item + "\t");
        // }
        StdOut.print("\n");
    }
}