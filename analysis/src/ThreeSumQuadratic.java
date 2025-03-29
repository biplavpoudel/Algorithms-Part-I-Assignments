/* *****************************************************************************
 *  Name:              Biplav Poudel
 *  Coursera User ID:  https://www.coursera.org/learner/biplavpoudel
 *  Last modified:     29 Mar, 2025
 **************************************************************************** */

// Design an algorithm for the 3-SUM problem
// that takes time proportional to n^2 (n-squared) in the worst case.
// Assume you can sort the n integers in time proportional to n^2 or better.

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class ThreeSumQuadratic {
    public static void main(String[] args) {
        int n = StdIn.readInt();
        Integer[] arr = new Integer[n];
        for (int i = 0; i < n; i++) {
            arr[i] = StdIn.readInt();
        }
        sort(arr);
        StdOut.printf("\nThe three-sums are:\n");
        threeSum(arr);
    }

    // sorting for binary search in n^2
    public static void sort(Integer[] arr) {
        // Insertion sort worst case is n^2
        // make an index key and push larger elements back by 1 and insert
        for (int i = 1; i < arr.length; i++) {
            int j = i - 1;
            int key = arr[i];
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    public static void threeSum(Integer[] arr) {
        // For each pair of numbers a[i] and a[j],
        // binary search for -(a[i] + a[j])
        int k = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    k = binarySearch(arr, -(arr[i] + arr[j]));
                    if (k == -1) continue;
                    else if (arr[j] > arr[k]) {
                        StdOut.printf("%d + %d + %d = 0\n", arr[i], arr[j], arr[k]);
                    }
                }
            }
        }
    }

    public static int binarySearch(Integer[] arr, int k) {
        int size = arr.length;
        int lo = 0;
        int hi = size - 1;

        while (lo <= hi) {
            int mid = (hi + lo) / 2;
            if (arr[mid] == k) return mid;
            else if (k > arr[mid]) {
                lo = mid + 1;
            }
            else if (k < arr[mid]) {
                hi = mid - 1;
            }
        }
        return -1;

    }

}
