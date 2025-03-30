/* *****************************************************************************
 *  Name:              Biplav Poudel
 *  Coursera User ID:  https://www.coursera.org/learner/biplavpoudel
 *  Last modified:     29 Mar, 2025
 **************************************************************************** */

// An array is bitonic if it comprises an increasing sequence of integers followed immediately by a decreasing sequence of integers.
// Write a program that, given a bitonic array of n distinct integer values,
// determines whether a given integer is in the array.

// Standard version: Use ∼ 3lgn compares in the worst case.
// Signing bonus: Use ∼ 2 lg𝑛 compares in the worst case
// (and prove that no algorithm can guarantee to perform fewer than ∼ 2lgn compares in the worst case).

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class BitonicArraySearch {
    public static void main(String[] args) {
        int size = StdIn.readInt();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = StdIn.readInt();
        }
        int key = StdIn.readInt();
        search(arr, key);
    }

    public static void search(int[] arr, int key) {
        // we can find the inflection point using binary search?
        StdOut.printf("Now we divide the bitonic arrays into two...");
        int peak = divide(arr);
        StdOut.printf("The maximum element is: %d\n", arr[peak]);
        StdOut.printf("The two arrays are:\n");
        for (int i = 0; i <= peak; i++) {
            StdOut.print(arr[i] + " ");
        }
        StdOut.printf("\n");
        for (int i = peak + 1; i < arr.length; i++) {
            StdOut.print(arr[i] + " ");
        }
    }

    // arr = {1,2,5,4,3,2,1}
    public static int divide(int[] arr) {
        // find maximum integer in O(log n) comparison
        int left = 0;
        int right = arr.length;

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (mid == 0 || arr[mid] >= arr[mid + 1] && arr[mid] >= arr[mid - 1]) return mid;
            else if (arr[mid] < arr[mid + 1]) {
                left = mid;
            }
            else {
                right = mid;
            }
        }
        return -1;
    }

    public static int fwdBinarySearch(Integer[] arr, int k) {
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

    public static int revBinarySearch(Integer[] arr, int k) {
        int size = arr.length;
        int lo = size - 1;
        int hi = 0;

        while (hi <= lo) {
            int mid = (hi + lo) / 2;
            if (arr[mid] == k) return mid;
            else if (k > arr[mid]) {
                hi = mid - 1;
            }
            else if (k < arr[mid]) {
                lo = mid + 1;
            }
        }
        return -1;
    }
}
