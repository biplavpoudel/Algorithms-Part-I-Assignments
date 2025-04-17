/* *****************************************************************************
 *  Name:              Biplav Poudel
 *  Coursera User ID:  https://www.coursera.org/learner/biplavpoudel
 *  Last modified:     14 Apr, 2025
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]); // permutationSize
        int i = 0;

        RandomizedQueue<String> queue = new RandomizedQueue<>();
        // count is not given so enqueue as I go
        while (!StdIn.isEmpty()) {
            queue.enqueue(StdIn.readString());
        }

        Iterator<String> iter = queue.iterator();
        while (iter.hasNext() && i++ < k) {
            String item = iter.next();
            StdOut.println(item);
        }
        StdOut.print("\n");
    }
}
