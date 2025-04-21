/* *****************************************************************************
 *  Name:              Biplav Poudel
 *  Coursera User ID:  https://www.coursera.org/learner/biplavpoudel
 *  Last modified:     21 Apr, 2025
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Intersection {
    public static class Point2D implements Comparable<Point2D> {
        private final int x;
        private final int y;

        public Point2D(int x, int y) {
            this.x = x;
            this.y = y;
        }

        // use this to count intersection.
        // so if a[i] is greater than b[j], we can choose next b, b[j+1], in sorted array
        // to see if diff will be 0
        public int compareTo(Point2D item) {
            if (this.x != item.x) return (this.x - item.x);
            else return (this.y - item.y);
        }

        public String toString() {
            return "(" + x + "," + y + ")";
        }
    }

    public static class ShellSort {

        public static void sort(Comparable[] arr) {
            // we implement shell sort with increment: 3x+1
            int N = arr.length;
            int h = 1;
            while (h < N / 3) h = h * 3 + 1;

            while (h >= 1) {
                for (int i = h; i < N; i++) {
                    for (int k = i; k >= h && less(arr[k], arr[k - h]); k -= h) {
                        exchange(arr, k, k - h);
                    }
                }
                h = h / 3;
            }
        }

        public static void exchange(Comparable[] a, int i, int j) {
            Comparable swap = a[i];
            a[i] = a[j];
            a[j] = swap;
        }

        public static boolean less(Comparable x, Comparable y) {
            return x.compareTo(y) < 0;
        }

        public static boolean isSorted(Comparable[] a) {
            int N = a.length;
            for (int i = 1; i < N; i++) {
                if (less(a[i], a[i - 1])) return false;
            }
            return true;
        }
    }

    public static int intersectionsCounter(Point2D[] a, Point2D[] b) {
        int count = 0;
        if (!ShellSort.isSorted(a)) ShellSort.sort(a);
        if (!ShellSort.isSorted(b)) ShellSort.sort(b);

        // actual logic to count intersections in sub-quad time or better
        int size = a.length;

        int i = 0, j = 0;
        while (i < size && j < size) {
            int compare = a[i].compareTo(b[j]);
            if (compare > 0) j++; // if a[i] is bigger, get bigger b[j]
            else if (compare < 0) i++; // if a[i] smaller, get bigger a[i]
            else {
                count++;
                i++;
                j++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        StdOut.printf("Enter the size of arrays:");
        int N = StdIn.readInt();
        Point2D[] a = new Point2D[N];
        Point2D[] b = new Point2D[N];

        StdOut.print("\nEnter the elements of array a[] as (x,y) pairs:\n");
        for (int i = 0; i < N; i++) {
            int x = StdIn.readInt();
            int y = StdIn.readInt();
            a[i] = new Point2D(x, y);
        }
        StdOut.print("\nEnter the elements of array b[] as (x,y) pairs:\n");
        for (int i = 0; i < N; i++) {
            int x = StdIn.readInt();
            int y = StdIn.readInt();
            b[i] = new Point2D(x, y);
        }

        StdOut.printf("The number of intersections are: %d\n", intersectionsCounter(a, b));
    }
}
