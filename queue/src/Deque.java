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

    private void resize(int capacity) {
        // @SuppressWarnings("unchecked")
        Item[] newArr = (Item[]) new Object[capacity];
        int old_length = size();
        for (int i = 0; i < old_length; i++) {
            newArr[i] = arr[(head + i) % arr.length];
        }
        arr = newArr;
        head = 0;
        tail = old_length - 1;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        // if array full, resize first
        if (count == arr.length)
            resize(arr.length * 2);
        // if empty array, when an element is added, tail = head = 0
        if (isEmpty()) tail = head;
        // then shift head to the left and add new item to it
        // StdOut.printf("Array length is: %d", arr.length);
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
        if (count > 0 && count == arr.length) resize(arr.length * 2);
        // then shift head to the left and add new item to it
        tail = (tail + 1 + arr.length) % arr.length;    // if empty array, tail will be 0 (-1+1=0)
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
        if (isEmpty()) head = 0;
        else head = (head + 1 + arr.length) % arr.length;
        count--;
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
        if (isEmpty()) tail = 0;
        else tail = (tail - 1 + arr.length) % arr.length;
        count--;
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
            if (i > count) throw new NoSuchElementException();
            return arr[(head + i++) % arr.length];
        }

        public void remove() {
            throw new NoSuchElementException();
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

        deque.addFirst(1);
        deque.removeLast();

        // StdOut.printf("Head:%d, tail:%d, count:%d\n", deque.head, deque.tail, deque.count);
        // deque.addLast(1);
        // StdOut.printf("Head:%d, tail:%d, count:%d\n", deque.head, deque.tail, deque.count);
        // deque.removeFirst();     //==> 1
        // StdOut.printf("Head:%d, tail:%d, count:%d\n", deque.head, deque.tail, deque.count);
        // deque.isEmpty();         //==> true
        // deque.isEmpty();         //==> true
        // deque.addFirst(5);
        // StdOut.printf("Head:%d, tail:%d, count:%d\n", deque.head, deque.tail, deque.count);
        // deque.size();          //==> 1
        // deque.removeLast();
        // StdOut.printf("Head:%d, tail:%d, count:%d\n", deque.head, deque.tail, deque.count);


        StdOut.printf("\nThe new deque is:\n");
        for (Integer item : deque) {
            StdOut.print(item + "\t");
        }
        StdOut.print("\n");
    }
}