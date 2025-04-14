/* *****************************************************************************
 *  Name:              Biplav Poudel
 *  Coursera User ID:  https://www.coursera.org/learner/biplavpoudel
 *  Last modified:     14 Apr, 2025
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Item[] arr;
    private int head;        // let's use circular array with no fixed ends
    private int tail;
    private int N;


    // construct an empty deque
    public Deque() {
        @SuppressWarnings("unchecked")
        Item[] arr = (Item[]) new Object[1];
    }

    // is the deque empty?
    public boolean isEmpty() {
        return head == tail && arr[head].equals("null");
    }

    // return the number of items on the deque
    public int size() {
        return arr.length;
    }

    public void resize(int capacity) {
        @SuppressWarnings("unchecked")
        Item[] new_arr = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            new_arr[i] = arr[i];
        }
        arr = new_arr;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        // if array full, resize first
        if ((tail + 1) % arr.length == head % arr.length) resize(arr.length * 2);
        // then shift head to the left and add new item to it
        head = (head - 1) % arr.length;
        arr[head] = item;

    }

    // add the item to the back
    public void addLast(Item item) {
    }

    // remove and return the item from the front
    public Item removeFirst() {
    }

    // remove and return the item from the back
    public Item removeLast() {
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    public class ArrayIterator implements Iterator<Item> {

        public boolean hasNext() {
            return false;
        }

        public Item next() {
            return null;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
    }
}