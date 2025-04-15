/* *****************************************************************************
 *  Name:              Biplav Poudel
 *  Coursera User ID:  https://www.coursera.org/learner/biplavpoudel
 *  Last modified:     14 Apr, 2025
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] arr;
    private int count;
    private int top;

    // construct an empty randomized queue
    @SuppressWarnings("unchecked")
    public RandomizedQueue(int N) {
        arr = (Item[]) new Object[N];
        top = arr.length - 1;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return count == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return count;
    }

    public void resize(int capacity) {
        @SuppressWarnings("unchecked")
        Item[] new_arr = (Item[]) new Object[capacity];
        for (int i = 0; i < size(); i++) {
            new_arr[i] = arr[i];
        }
        arr = new_arr;
    }

    public void reorder() {
        @SuppressWarnings("unchecked")
        Item[] new_arr = (Item[]) new Object[count];
        for (int i = 0, j = 0; i < size(); i++, j++) {
            while (arr[j] == null) j++;
            new_arr[i] = arr[j];
        }
        arr = new_arr;
        top = count - 1;
    }

    // add the item
    public void enqueue(Item item) {
        if (count == arr.length) resize(arr.length * 2);
        // there will be null items if randomly removed so reorder
        if (count < arr.length && (top + 1) == arr.length) reorder();
        arr[++top] = item;
        count++;
    }

    // remove and return a random item
    public Item dequeue() {
        int randIndex;
        // selects new index if the item at given index is null already
        do {
            randIndex = StdRandom.uniformInt(0, arr.length);
        } while (arr[randIndex] != null);

        Item randItem = arr[randIndex];
        arr[randIndex] = null;
        count--;
        return randItem;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        int randIndex = StdRandom.uniformInt(0, arr.length);
        Item randItem = arr[randIndex];
        return randItem;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
    }

    // unit testing (required)
    public static void main(String[] args) {
    }
}
