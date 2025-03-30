/* *****************************************************************************
 *  Name:              Biplav Poudel
 *  Coursera User ID:  https://www.coursera.org/learner/biplavpoudel
 *  Last modified:     29 Mar, 2025
 **************************************************************************** */

// An array is bitonic if it is comprised of an increasing sequence of integers followed immediately by a decreasing sequence of integers.
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
        Integer[] arr = new Integer[size];
        for (int i = 0; i < size; i++) {
            arr[i] = StdIn.readInt();
        }
        StdOut.printf("Enter an integer to be searched: ");
        int key = StdIn.readInt();
        search(arr, key);
    }

    public static void search(Integer[] arr, int key) {
        // we can find the inflection point using binary search?
        Integer[] div = new Integer[2];
    }

    // arr = {1,2,5,4,3,2,1}
    public static Integer[] divide(Integer[] arr) {
        int size = arr.length;
        Integer[] div = new Integer[2]; // div0 = hi_asc, div1 = hi_desc

        int lo_asc = 0;
        int hi_asc = size - 1;
        int hi_desc = size - 1;
        int lo_desc = size - 1;

        while (hi_asc <= hi_desc) {
            int inflection = (lo_asc + hi_asc) / 2;   // need to change this asap
            int preceding = inflection--;
            int succeeding = inflection++;

            // inflection and hi_asc are kept same
            if (arr[inflection] >= arr[preceding] && arr[inflection] >= arr[succeeding]) {
                hi_asc = inflection;
                hi_desc = succeeding;
                div[0] = hi_asc;
                div[1] = hi_desc;
                return div;
            }
            else if (arr[inflection] < arr[succeeding]) {
                hi_asc = succeeding;
                hi_desc = succeeding + 1;
            }
            else if (arr[inflection] < arr[preceding]) {
                hi_asc = preceding;
                hi_desc = inflection;
            }
        }
        return null;
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
