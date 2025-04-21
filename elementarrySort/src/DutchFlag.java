/* *****************************************************************************
 *  Name:              Biplav Poudel
 *  Coursera User ID:  https://www.coursera.org/learner/biplavpoudel
 *  Last modified:     20 Apr, 2025
 **************************************************************************** */

// Given an array of n buckets, each containing a red, white, or blue pebble, sort them by color.

// The allowed operations are:
// swap(i,j)  swap the pebble in bucket i with the pebble in bucket j
// color(i): determine the color of the pebble in bucket i

// Performance requirements:
// At most n calls to color()
// At most n calls to swap()
// Constant extra space.

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class DutchFlag {
    public static class Bucket {
        private String pebble;

        public Bucket(String pebble) {
            this.pebble = pebble;
        }
    }

    public static class Sort {
        public static void sort(Bucket[] buckets, int N) {
            int swapCount = 0;
            int lo = 0, mid = 0;
            int hi = N - 1;
            while (mid <= hi) {
                if (buckets[mid].pebble.equals("red")) {
                    swap(buckets[mid], buckets[lo]);
                    lo++;
                    mid++;
                    swapCount++;
                }
                else if (buckets[mid].pebble.equals("blue")) {
                    mid++;
                }
                else {  // mid = white
                    swap(buckets[hi], buckets[mid]);
                    swapCount++;
                    hi--;
                }

            }
            StdOut.printf("\nNumber of swap() calls is: %d", swapCount);

        }

        public static void swap(Bucket a, Bucket b) {
            String swap = a.pebble;
            a.pebble = b.pebble;
            b.pebble = swap;
        }

    }

    public static String color(Bucket item) {
        return item.pebble;
    }

    public static void main(String[] args) {
        StdOut.printf("Enter number of buckets: ");
        int N = StdIn.readInt();
        Bucket[] bucket = new Bucket[N];

        for (int i = 0; i < N; i++) {
            int color = StdRandom.uniformInt(0, 3);
            if (color == 0) bucket[i] = new Bucket("red");
            else if (color == 1) bucket[i] = new Bucket("blue");
            else bucket[i] = new Bucket("white");
        }
        StdOut.printf("The unsorted bucket is:\n");
        for (int i = 0; i < N; i++) {
            StdOut.printf(bucket[i].pebble + " ");
        }

        Sort.sort(bucket, N);

        StdOut.printf("\n\nThe sorted bucket is:\n");
        for (int i = 0; i < N; i++) {
            StdOut.printf(bucket[i].pebble + " ");
        }
        StdOut.print("\n");
    }
}
