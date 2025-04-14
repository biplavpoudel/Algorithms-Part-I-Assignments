/* *****************************************************************************
 *  Name:              Biplav Poudel
 *  Coursera User ID:  https://www.coursera.org/learner/biplavpoudel
 *  Last modified:     11 Apr, 2025
 **************************************************************************** */

// Implement a queue with two stacks
// so that each queue operations takes a constant amortized number of stack operations.

import java.util.Iterator;
import java.util.NoSuchElementException;

public class QueueTwoStacks {

    private class Stack<Item> implements Iterable<Item> {
        private int N;
        private Item[] stack_arr;

        // generics array is not allowed so need to cast as Item[]
        @SuppressWarnings("unchecked")
        private Stack() {
            stack_arr = (Item[]) new Object[1];
        }

        private boolean isEmpty() {
            return N == 0;
        }

        private Item pop() {
            if (isEmpty()) {
                throw new NoSuchElementException("Stack underflow!");
            }
            Item last = (Item) stack_arr[--N];
            stack_arr[N] = null;
            if (N > 0 && N == stack_arr.length / 4) {
                resize(stack_arr.length / 2);
            }
            return last;
        }

        private void push(Item item) {
            if (N == stack_arr.length) {
                resize(stack_arr.length * 2);
            }
            stack_arr[N++] = item;
        }

        private void resize(int capacity) {
            @SuppressWarnings("unchecked")
            Item[] new_arr = (Item[]) new Object[capacity];
            for (int i = 0; i < N; i++) {
                new_arr[i] = stack_arr[i];
            }
            stack_arr = new_arr;
        }


        public Iterator<Item> iterator() {
            return new ReverseArrayIterator();
        }

        private class ReverseArrayIterator implements Iterator<Item> {
            private int i = N;

            public boolean hasNext() {
                return i > 0;
            }

            public Item next() {
                return stack_arr[--i];
            }
        }

    }

    public static void main(String[] args) {
        String[] data = { "to", "be", "or", "not", "to", "be", "is", "your", "choice" };
        String popped;
        QueueTwoStacks queue = new QueueTwoStacks();
        QueueTwoStacks.Stack<String> stack1 = queue.new Stack<>();
        QueueTwoStacks.Stack<String> stack2 = queue.new Stack<>();
        for (String item : data) {
            stack1.push(item);
        }
        System.out.println("First Stack Contents (LIFO order):");
        for (String item : stack1) {
            System.out.print(item + " ");
        }
        System.out.print("\n");
        while (!stack1.isEmpty()) {
            popped = stack1.pop();
            stack2.push(popped);
        }
        System.out.println("Second Stack Contents similar to Queue in FIFO:");
        for (String item : stack2) {
            System.out.print(item + " ");
        }
        System.out.print("\n");
    }
}
