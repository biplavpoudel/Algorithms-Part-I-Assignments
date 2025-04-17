/* *****************************************************************************
 *  Name:              Biplav Poudel
 *  Coursera User ID:  https://www.coursera.org/learner/biplavpoudel
 *  Last modified:     14 Apr, 2025
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] arr;
    private int count;

    // construct an empty randomized queue
    // @SuppressWarnings("unchecked")
    public RandomizedQueue() {
        arr = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return count == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return count;
    }

    private void resize(int capacity) {
        // @SuppressWarnings("unchecked")
        Item[] newArr = (Item[]) new Object[capacity];
        for (int i = 0; i < size(); i++) {
            newArr[i] = arr[i];
        }
        arr = newArr;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (count > 0 && count == arr.length) resize(arr.length * 2);
        arr[count++] = item;  // if count=3, enqueue at 3 and increment count to 4
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        // swap last non-null element to the null position
        int randIndex = StdRandom.uniformInt(0, count);
        Item randItem = arr[randIndex];

        arr[randIndex] = arr[count - 1];  // assign last element to this index
        arr[count - 1] = null;
        count--;
        return randItem;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int randIndex = StdRandom.uniformInt(0, count);
        Item randItem = arr[randIndex];
        return randItem;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandIterator();
    }

    private class RandIterator implements Iterator<Item> {
        private int remaining;

        public RandIterator() {
            StdRandom.shuffle(arr, 0, count);
            remaining = count;
        }

        public boolean hasNext() {
            return remaining > 0;
        }

        public Item next() {
            --remaining;
            if (remaining < 0) throw new NoSuchElementException();
            return arr[remaining];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            queue.enqueue(StdIn.readInt());
        }
        StdOut.printf("Item: %d was dequeued\n", queue.dequeue());
        StdOut.printf("Random sample item: %d\n", queue.sample());
        StdOut.print("The first random ordering of the queue is:\n");
        for (Integer item : queue) {
            StdOut.print(item + "\t");
        }
        StdOut.print("\nThe second independent random ordering of the queue is:\n");
        for (Integer item : queue) {
            StdOut.print(item + "\t");
        }
        StdOut.print("\nThe third independent random ordering of the queue is:\n");
        for (Integer item : queue) {
            StdOut.print(item + "\t");
        }
        StdOut.print("\n");

    }
}
